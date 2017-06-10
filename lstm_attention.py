#!/usr/bin/env python
# -*- coding: utf-8 -*-

from __future__ import print_function

from keras import backend as K
import os
import sys
import numpy as np
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences
from keras.utils import to_categorical
from keras.layers import Dense, Input, Activation, TimeDistributed, merge
from keras.layers import Embedding, LSTM, Flatten, RepeatVector, Permute, Lambda
from keras.models import Model
from sklearn.metrics.pairwise import cosine_similarity


BASE_DIR = '/home/ercan'
TURQUAS_DIR = BASE_DIR + '/BitirmeProjesi'
WORD2VEC_VALUES = 'embeddings.txt'
SENTENCES = 'sentences.txt'
CANDIDATES = 'candidates.txt'
QUESTION = 'question.txt'
TEST_DATA = 'test_data.txt'
MAX_SEQUENCE_LENGTH = 20
EMBEDDING_DIM = 200
ANSWER_COUNT = 10


# load sentences from text file
print('Loading sentences.')
sentences = []
labels_index = {}
f = open(os.path.join(TURQUAS_DIR, SENTENCES))
for line in f:
    sentences.append(line.lower()) # read sentences from txt
f.close()
print('Found %s sentences.' % len(sentences))



#load word vectors and index them to a dictionary
print('Indexing word vectors.')
embeddings_index = {}
f = open(os.path.join(TURQUAS_DIR, WORD2VEC_VALUES))
for line in f:
    values = line.split() # split word and vector
    word = values[0]
    vector = np.asarray(values[1:], dtype='float32')
    embeddings_index[word] = vector # assign vector value to word in dict
f.close()
print('Found %s word vectors.' % len(embeddings_index))


#tokenize sentences and get word_index
print('Tokenizing the sentences')
tokenizer = Tokenizer(num_words=len(embeddings_index)) # create Tokenizer
tokenizer.fit_on_texts(sentences) # fit sentences into Tokenizer
sequences = tokenizer.texts_to_sequences(sentences) # convert texts to sequences
word_index = tokenizer.word_index # create word-index dictionary
data = pad_sequences(sequences, maxlen=MAX_SEQUENCE_LENGTH) # create sentences with pads

for word, vector in embeddings_index.items():
	if word not in tokenizer.word_index:
		tokenizer.word_index[word] = len(tokenizer.word_index) + 1

print('Found %s unique tokens.' % len(tokenizer.word_index))


#create embedding matrix for embedding layer
num_words = len(tokenizer.word_index) + 1 # set total number of words 
embedding_matrix = np.zeros((num_words, EMBEDDING_DIM)) # create embedding matrix for word vectors
for word, i in tokenizer.word_index.items(): # for every word in word_index dict
    if i >= num_words:
        continue
    embedding_vector = embeddings_index.get(word) # get vector value of word
    if embedding_vector is not None:
        # words not found in embedding index will be all zeros
        embedding_matrix[i] = embedding_vector # assign vector of that word to its index from word_index dict


# load pre-trained word embeddings into an Embedding layer
# trainable = False means the embeddings are fixed
embedding_layer = Embedding(num_words,
                            EMBEDDING_DIM,
                            weights=[embedding_matrix],
                            input_length=MAX_SEQUENCE_LENGTH,
                            trainable=False)
sequence_input = Input(shape=(MAX_SEQUENCE_LENGTH,), dtype='int32')
embedded_sequences = embedding_layer(sequence_input)
lstm = LSTM(256, return_sequences=True)(embedded_sequences)
lstm2 = LSTM(256, return_sequences=True)(lstm)
lstm3 = LSTM(256, return_sequences=True)(lstm2)

attention = Dense(1, activation='tanh')(lstm3)
attention = Flatten()(attention)
attention = Activation('softmax')(attention)
attention = RepeatVector(256)(attention)
attention = Permute([2, 1])(attention)

sent_representation = merge([lstm3, attention], mode='mul')
sent_representation = Lambda(lambda xin: K.sum(xin, axis=-2), output_shape=(256,))(sent_representation)

probabilities = Dense(200, activation='tanh')(sent_representation)

model = Model(sequence_input, probabilities)
model.compile(loss='mse',
              optimizer='adam')
sentence_bank = model.predict(data)


def readQuestion():
	question = []
	f = open(os.path.join(TURQUAS_DIR, QUESTION))
	for line in f:
	    question.append(line.lower())
	f.close()
	return question

# load candidates from text file
def readCandidates():
	print('Loading candidates.')
	candidates = []
	labels_index = {}
	f = open(os.path.join(TURQUAS_DIR, CANDIDATES))
	for line in f:
	    candidates.append(line.lower())
	f.close()
	print('Found %s candidates.' % len(candidates))
	return candidates

while True:
	test_sentences = []
	sentence = raw_input("ask me something: ")
	test_sentences.append(sentence.lower())
	sequence = tokenizer.texts_to_sequences(test_sentences) # convert texts to sequences
	print(sequence)
	sample = pad_sequences(sequence, maxlen=MAX_SEQUENCE_LENGTH)
	print(sample)
	sample_pred = model.predict(sample)

	cosine_sim = cosine_similarity(sample_pred, sentence_bank)
	sorted_list = np.argsort(-cosine_sim)
	sorted_list = sorted_list[0][:ANSWER_COUNT]
	print(cosine_sim)
	print(sorted_list)
	for index in sorted_list:
		print("Answer: %s \nScore: %.4f " %(sentences[index], cosine_sim[0][index]))
