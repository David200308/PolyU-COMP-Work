cards <- c("Agrippa", "Ptolemy", "Others")
prob <- c(0.01, 0.01, 0.98)
N = 100000

classes_r <- data.frame(a_and_p = 0, a_or_p = 0, n_a_p = 0)
names(classes_r) <- c("A and P", "A or P", "Not A and P")

i <- 1
while (i <= N) {
  t <- table(sample(cards, 500, replace = TRUE, prob))
  if (length(t) == 3) {
    classes_r$"A and P" = classes_r$"A and P" + 1
  } else if (length(t) == 2) {
    classes_r$"A or P" = classes_r$"A or P" + 1
  }else {
    classes_r$"Not A and P" = classes_r$"Not A and P" + 1
  }
  
  i = i + 1
}

result = ((classes_r$"Not A and P")/ N)* prob[3]
print("The result is: ")
print(result)
