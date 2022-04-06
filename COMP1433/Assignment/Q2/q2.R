install.packages("tokenizers")
file <- "./data/Q2/train_tweets.txt"
# (a)
tweets <- read.csv(file, header = FALSE, sep = "\t", fill=TRUE)
colnames(tweets) <- c('tweetID','userID','sentiment','text')
head(tweets)

# (b)
library("tokenizers")
tokens <- tokenize_words(tweets$text)
tokens
tweets$tokens <- tokens

# (c)
vocab <- c()
tokens_1 <- unlist(tokens)
token_table <- table(tokens_1)
only_words <- names(token_table)
i <- 1

while (i <= 21673) {
  temp <- only_words[i]
  count <- 0
  j <- 1
  while (j <= 8021) {
    if (temp %in% tweets$tokens[[j]]) {
      count = count + 1
      if (count > 3) {
        vocab <- c(vocab, temp)
        break
      }
    }
    j = j + 1
  }
  i <- i + 1
}
vocab

# (d)

#for sentence
positive_txt <-c()
negative_txt <- c()
neutral_txt <- c()

i <- 1
while (i <= 8021) {
  if (tweets$sentiment[i] == 'positive') {
    temp <- tweets$text[i]
    positive_txt <- c(positive_txt, temp)
  } else if (tweets$sentiment[i] == 'negative') {
    temp <- tweets$text[i]
    negative_txt <- c(negative_txt, temp)
  } else if (tweets$sentiment[i] == 'neutral') {
    temp <- tweets$text[i]
    neutral_txt <- c(neutral_txt, temp)
  }
  i = i + 1
}

#for words
positive_txt_words <-c()
negative_txt_words <- c()
neutral_txt_words <- c()

i <- 1
while (i <= 8021) {
  if (tweets$sentiment[i] == 'positive') {
    temp <- tweets$tokens[i]
    positive_txt_words <- c(positive_txt_words, temp)
  } else if (tweets$sentiment[i] == 'negative') {
    temp <- tweets$tokens[i]
    negative_txt_words <- c(negative_txt_words, temp)
  } else if (tweets$sentiment[i] == 'neutral') {
    temp <- tweets$tokens[i]
    neutral_txt_words <- c(neutral_txt_words, temp)
  }
  i = i + 1
}  

# for positive
tokens_positive_txt <- unlist(positive_txt_words)
token_positive_txt_table <- table(tokens_positive_txt)
only_positive_txt_words <- names(token_positive_txt_table)

words_in_positive <- c()
i <- 1
while (i <= length(positive_txt_words)) {
  temp = vocab[i]
  j <- 1
  while (j <= length(positive_txt_words)) {
    if (temp == only_positive_txt_words[j]) {
      words_in_positive <- c(words_in_positive,temp)
    }
    j = j + 1
  }
  i = i + 1
}

# for negative
tokens_negative_txt <- unlist(negative_txt_words)
token_negative_txt_table <- table(tokens_negative_txt)
only_negative_txt_words <- names(token_negative_txt_table)

words_in_negative <- c()
i <- 1
while (i <= length(negative_txt_words)) {
  temp = vocab[i]
  j <- 1
  while (j <= length(negative_txt_words)) {
    if (temp == only_negative_txt_words[j]) {
      words_in_negative <- c(words_in_negative,temp)
    }
    j = j + 1
  }
  i = i + 1
}


# for neutral
tokens_neutral_txt <- unlist(neutral_txt_words)
token_neutral_txt_table <- table(tokens_neutral_txt)
only_neutral_txt_words <- names(token_neutral_txt_table)

words_in_neutral <- c()
i <- 1
while (i <= length(neutral_txt_words)) {
  temp = vocab[i]
  j <- 1
  while (j <= length(neutral_txt_words)) {
    if (temp == only_neutral_txt_words[j]) {
      words_in_neutral <- c(words_in_neutral,temp)
    }
    j = j + 1
  }
  i = i + 1
}

# for p_positive
i <- 1
positive_df <- data.frame()
words_df <- data.frame()
while (i <= length(vocab)) {
  if (vocab[i] %in% tokens_positive_txt) {
    num = token_positive_txt_table[vocab[i]]
    p_positive <- num / (length(only_positive_txt_words) + length(vocab))
    words_df <- data.frame(p_positive)
    positive_df <- rbind(positive_df, words_df)
  } else {
    p_positive <- 1 / (length(only_positive_txt_words) + length(vocab))
    words_df <- data.frame(p_positive)
    row.names(words_df) <- vocab[i]
    positive_df <- rbind(positive_df, words_df)
  }
  i = i + 1
}


# for p_negative
i <- 1
negative_df <- data.frame()
words_df <- data.frame()
while (i <= length(vocab)) {
  if (vocab[i] %in% tokens_negative_txt) {
    num = token_negative_txt_table[vocab[i]]
    p_negative <- num / (length(only_negative_txt_words) + length(vocab))
    words_df <- data.frame(p_negative)
    negative_df <- rbind(negative_df, words_df)
  } else {
    p_negative <- 1 / (length(only_negative_txt_words) + length(vocab))
    words_df <- data.frame(p_negative)
    row.names(words_df) <- vocab[i]
    negative_df <- rbind(negative_df, words_df)
  }
  i = i + 1
}


# for p_neutral
i <- 1
words_df <- data.frame()
neutral_df <- data.frame()
while (i <= length(vocab)) {
  if (vocab[i] %in% tokens_neutral_txt) {
    num = token_neutral_txt_table[vocab[i]]
    p_neutral <- num / (length(only_neutral_txt_words) + length(vocab))
    words_df <- data.frame(p_neutral)
    neutral_df <- rbind(neutral_df, words_df)
  } else {
    p_neutral <- 1 / (length(only_neutral_txt_words) + length(vocab))
    words_df <- data.frame(p_neutral)
    row.names(words_df) <- vocab[i]
    neutral_df <- rbind(neutral_df, words_df)
  }
  i = i + 1
}


test1 <- "I love the banner that was unfurled in the United end last night. It read: Chelsea - Standing up against racism since Sunday"
test2 <- "So Clattenburg's alleged racism may mean end of his career; Terry, Suarez, Rio use it and can't play for a couple of weeks?"
test3 <- "In our busy lives in Dubai could we just spare a moment of silence this Friday morning for the people who still wear crocs."

library("tokenizers")
# split test1 to words
test1_tokens <- tokenize_words(test1)
tokens_test1 <- unlist(test1_tokens)

# split test1 to words
test2_tokens <- tokenize_words(test2)
tokens_test2 <- unlist(test2_tokens)

# split test1 to words
test3_tokens <- tokenize_words(test3)
tokens_test3 <- unlist(test3_tokens)

level <- c("positive", "negative", "neutral")


#for test1 Positive likelihood
i <- 1
log_p_positive <- 0
while (i <= length(tokens_test1)) {
  j <- 1
  while (j <= length(vocab)) {
    if (tokens_test1[i] %in% vocab[j]) {
      print(positive_df[tokens_test1[i], ])
      log_p_positive = log_p_positive + log(positive_df[tokens_test1[i], ])
    }
    j = j + 1
  }
  i = i + 1
}

likelihood_positive_test1 <- ( length(positive_txt)/ 8021 ) + log_p_positive


#for test1 Negative likelihood
i <- 1
log_p_negative <- 0
while (i <= length(tokens_test1)) {
  j <- 1
  while (j <= length(vocab)) {
    if (tokens_test1[i] %in% vocab[j]) {
      print(negative_df[tokens_test1[i], ])
      log_p_negative = log_p_negative + log(negative_df[tokens_test1[i], ])
    }
    j = j + 1
  }
  i = i + 1
}

likelihood_negative_test1 <- ( length(negative_txt)/ 8021 ) + log_p_negative


#for test1 Neutral likelihood
i <- 1
log_p_neutral <- 0
while (i <= length(tokens_test1)) {
  j <- 1
  while (j <= length(vocab)) {
    if (tokens_test1[i] %in% vocab[j]) {
      print(neutral_df[tokens_test1[i], ])
      log_p_neutral = log_p_neutral + log(neutral_df[tokens_test1[i], ])
    }
    j = j + 1
  }
  i = i + 1
}

likelihood_neutral_test1 <- ( length(neutral_txt)/ 8021 ) + log_p_neutral

test1_pre_result <- c(likelihood_positive_test1, likelihood_negative_test1 , likelihood_neutral_test1)
test1_result_index <- which.max(test1_pre_result)




#for test2 Positive likelihood
i <- 1
log_p_positive <- 0
while (i <= length(tokens_test2)) {
  j <- 1
  while (j <= length(vocab)) {
    if (tokens_test2[i] %in% vocab[j]) {
      print(positive_df[tokens_test2[i], ])
      log_p_positive = log_p_positive + log(positive_df[tokens_test2[i], ])
    }
    j = j + 1
  }
  i = i + 1
}

likelihood_positive_test2 <- ( length(positive_txt)/ 8021 ) + log_p_positive


#for test2 Negative likelihood
i <- 1
log_p_negative <- 0
while (i <= length(tokens_test2)) {
  j <- 1
  while (j <= length(vocab)) {
    if (tokens_test2[i] %in% vocab[j]) {
      print(negative_df[tokens_test2[i], ])
      log_p_negative = log_p_negative + log(negative_df[tokens_test2[i], ])
    }
    j = j + 1
  }
  i = i + 1
}

likelihood_negative_test2 <- ( length(negative_txt)/ 8021 ) + log_p_negative


#for test2 Neutral likelihood
i <- 1
log_p_neutral <- 0
while (i <= length(tokens_test2)) {
  j <- 1
  while (j <= length(vocab)) {
    if (tokens_test2[i] %in% vocab[j]) {
      print(neutral_df[tokens_test2[i], ])
      log_p_neutral = log_p_neutral + log(neutral_df[tokens_test2[i], ])
    }
    j = j + 1
  }
  i = i + 1
}

likelihood_neutral_test2 <- ( length(neutral_txt)/ 8021 ) + log_p_neutral

test2_pre_result <- c(likelihood_positive_test2, likelihood_negative_test2 , likelihood_neutral_test2)
test2_result_index <- which.max(test2_pre_result)



#for test3 Positive likelihood
i <- 1
log_p_positive <- 0
while (i <= length(tokens_test3)) {
  j <- 1
  while (j <= length(vocab)) {
    if (tokens_test3[i] %in% vocab[j]) {
      print(positive_df[tokens_test3[i], ])
      log_p_positive = log_p_positive + log(positive_df[tokens_test3[i], ])
    }
    j = j + 1
  }
  i = i + 1
}

likelihood_positive_test3 <- ( length(positive_txt)/ 8021 ) + log_p_positive


#for test3 Negative likelihood
i <- 1
log_p_negative <- 0
while (i <= length(tokens_test3)) {
  j <- 1
  while (j <= length(vocab)) {
    if (tokens_test3[i] %in% vocab[j]) {
      print(negative_df[tokens_test3[i], ])
      log_p_negative = log_p_negative + log(negative_df[tokens_test3[i], ])
    }
    j = j + 1
  }
  i = i + 1
}

likelihood_negative_test3 <- ( length(negative_txt)/ 8021 ) + log_p_negative


#for test3 Neutral likelihood
i <- 1
log_p_neutral <- 0
while (i <= length(tokens_test3)) {
  j <- 1
  while (j <= length(vocab)) {
    if (tokens_test3[i] %in% vocab[j]) {
      print(neutral_df[tokens_test3[i], ])
      log_p_neutral = log_p_neutral + log(neutral_df[tokens_test3[i], ])
    }
    j = j + 1
  }
  i = i + 1
}

likelihood_neutral_test3 <- ( length(neutral_txt)/ 8021 ) + log_p_neutral

test3_pre_result <- c(likelihood_positive_test3, likelihood_negative_test3 , likelihood_neutral_test3)
test3_result_index <- which.max(test3_pre_result)



# result
print("The Test1 Prediction is: ")
print(level[test1_result_index])
print("The Test2 Prediction is: ")
print(level[test2_result_index])
print("The Test3 Prediction is: ")
print(level[test3_result_index])

