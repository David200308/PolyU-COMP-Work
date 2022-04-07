include_agrippa <- function(seed) {
  set.seed(seed)
  agrippa <- round(runif(1, 0, 1))
  return (agrippa)
}

include_ptolemy <- function(seed) {
  set.seed(seed)
  ptolemy <- round(runif(1,0,1))
  return(ptolemy)
}

simulate <- function(N, seed){
  set.seed(seed)
  x <- numeric()
  y <- numeric()
  while (i <= N) {
    if (i %in% 1:N) {
      agrippa = include_agrippa(i)
      ptolemy = include_ptolemy(i)
      cards <- round(runif(1, 0, 500))
      if(agrippa == 0 & ptolemy == 0 & cards == 500){
        return(1)
        }
         }
         i = i + 1
     }
  return(0)
}

i <- 1
while (i <= 1000) {
  count <- 0
  j <- 1
  while (j <= i) {
    values <- simulate(100,j)
    count <- count + values
    j = j + 1
  }
  i = i + 1
}
result = count / i
print(result)