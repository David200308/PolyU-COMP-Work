install.packages("ggplot2")
library("ggplot2")
file <- "./data/Q3/points.txt"
data <- read.table(file, header = FALSE, sep = ",")
head(data)

x <- c(data$V1)
y <- c(data$V2)
point <- data.frame(x, y)

distance_a <- c()
distance_b <- c()
distance_c <- c()

# for (0, 100)
i <- 1
while (i <= 90) {
  x2 <- x[i]
  y2 <- y[i]
  x1_a = 0
  y1_a = 100
  d_a = sqrt((x2-x1_a)^2+(y2-y1_a)^2)
  distance_a <- c(distance_a, d_a)
  i = i + 1
}

# for (40, 40)
i <- 1
while (i <= 90) {
  x2 <- x[i]
  y2 <- y[i]
  x1_b = 40
  y1_b = 40
  d_b = sqrt((x2-x1_b)^2+(y2-y1_b)^2)
  distance_b <- c(distance_b, d_b)
  i = i + 1
}


# for (100, 0)
i <- 1
while (i <= 90) {
  x2 <- x[i]
  y2 <- y[i]
  x1_c = 100
  y1_c = 0
  d_c = sqrt((x2-x1_c)^2+(y2-y1_c)^2)
  distance_c <- c(distance_c, d_c)
  i = i + 1
}

point$z <- c()
col <- c()
count_a <- c()
count_b <- c()
count_c <- c()

i <- 1
while (i <= 90) {
  if (distance_a[i] > distance_b[i]) {
    if (distance_b[i] > distance_c[i]) {
      col <- c(col, "c")
      count_c <- c(count_c, i)
    } else if (distance_b[i] < distance_c[i]) {
      col <-c(col, "b")
      count_b <- c(count_b, i)
    }
  } else if (distance_a[i] < distance_b[i]){
    if (distance_a[i] > distance_c[i]) {
      col <-c(col, "c")
      count_c <- c(count_c, i)
    } else if (distance_a[i] < distance_c[i]) {
      col <-c(col, "a")
      count_a <- c(count_a, i)
    }
  }
  i = i + 1
}





q <- 1
while (q <= 999) {
  # for a
  j <- 1
  x_sum <- 0
  y_sum <- 0
  while (j <= length(count_a)) {
    x_sum = x_sum + x[count_a[j]]
    y_sum = y_sum + y[count_a[j]]
    j = j + 1
  }
  x1_a = x_sum / j
  y1_a = y_sum / j
  
  # for b
  j <- 1
  x_sum <- 0
  y_sum <- 0
  while (j <= length(count_b)) {
    x_sum = x_sum + x[count_b[j]]
    y_sum = y_sum + y[count_b[j]]
    j = j + 1
  }
  x1_b = x_sum / j
  y1_b = y_sum / j
  
  #for c
  j <- 1
  x_sum <- 0
  y_sum <- 0
  while (j <= length(count_c)) {
    x_sum = x_sum + x[count_c[j]]
    y_sum = y_sum + y[count_c[j]]
    j = j + 1
  }
  x1_c = x_sum / j
  y1_c = y_sum / j
  
  
  distance_a <- c()
  distance_b <- c()
  distance_c <- c()
  
  # for a
  i <- 1
  while (i <= 90) {
    x2 <- x[i]
    y2 <- y[i]
    d_a = sqrt((x2-x1_a)^2+(y2-y1_a)^2)
    distance_a <- c(distance_a, d_a)
    i = i + 1
  }
  
  # for b
  i <- 1
  while (i <= 90) {
    x2 <- x[i]
    y2 <- y[i]
    d_b = sqrt((x2-x1_b)^2+(y2-y1_b)^2)
    distance_b <- c(distance_b, d_b)
    i = i + 1
  }
  
  
  # for c
  i <- 1
  while (i <= 90) {
    x2 <- x[i]
    y2 <- y[i]
    d_c = sqrt((x2-x1_c)^2+(y2-y1_c)^2)
    distance_c <- c(distance_c, d_c)
    i = i + 1
  }
  
  point$z <- c()
  col <- c()
  count_a <- c()
  count_b <- c()
  count_c <- c()
  
  i <- 1
  while (i <= 90) {
    if (distance_a[i] > distance_b[i]) {
      if (distance_b[i] > distance_c[i]) {
        col <- c(col, "c")
        count_c <- c(count_c, i)
      } else if (distance_b[i] < distance_c[i]) {
        col <-c(col, "b")
        count_b <- c(count_b, i)
      }
    } else if (distance_a[i] < distance_b[i]){
      if (distance_a[i] > distance_c[i]) {
        col <-c(col, "c")
        count_c <- c(count_c, i)
      } else if (distance_a[i] < distance_c[i]) {
        col <-c(col, "a")
        count_a <- c(count_a, i)
      }
    }
    i = i + 1
  }
  q = q + 1
}

point$z <- c(col)

q3plot <- ggplot(point, aes(x, y, color=z)) + geom_point(size = 1)

print(q3plot)


