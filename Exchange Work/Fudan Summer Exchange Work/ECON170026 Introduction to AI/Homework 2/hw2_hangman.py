class word_Counts:
    def __init__(self):
        self.total_count = 0
        self.word_counts = dict()
        self.most_five = []
        self.least_five = []
        self.sorted_counts = []

    def read_file(self, file_name):
        with open(file_name) as file:
            for line in file:
                temp = line.rstrip().split()
                word_count = int(temp[1])
                self.word_counts[temp[0]] = word_count
                self.total_count += word_count 

    def counts_sorting(self):
        self.sorted_counts = [(i, self.word_counts[i]) for i in sorted(self.word_counts, key = self.word_counts.get, reverse = True)]
        return self.sorted_counts

    def print_the_most(self):
        i = 0
        while (i < 10):
            print(self.sorted_counts[i])
            i = i + 1

    def print_the_least(self):
        i = 1
        while (i < 11):
            print(self.sorted_counts[-i])
            i = i + 1

    def word_probability(self, word):
        if word in self.word_counts:
            answer = self.word_counts[word] / self.total_count
            return answer
        return 0.0

class GuessProbability:
    def __init__(self, wc):
        self.wc = wc
        self.num = 0
        self.correct = list()
        self.incorrect = list()
        self.a1 = '_'
        self.a2 = '_'
        self.a3 = '_'
        self.a4 = '_'
        self.a5 = '_'
        self.a_list = [a1, a2, a3, a4, a5]
        self.current = self.a1 + self.a2 + self.a3 + self.a4 + self.a5
        self.known = list()
        self.not_used = list('ABCDEFGHIJKLMNOPQRSTUVWXYZ')

    def __init__(self, wc, num, a1, a2, a3, a4, a5, correct, incorrect):
        self.wc = wc
        self.num = num
        self.correct = correct
        self.incorrect = incorrect
        self.a1 = a1
        self.a2 = a2
        self.a3 = a3
        self.a4 = a4
        self.a5 = a5
        self.a_list = [a1, a2, a3, a4, a5]
        self.current = self.a1 + self.a2 + self.a3 + self.a4 + self.a5
        self.known = list()
        self.not_used = list('ABCDEFGHIJKLMNOPQRSTUVWXYZ')
        for l in self.correct:
            self.not_used.remove(l)

        for l in self.incorrect:
            self.not_used.remove(l)

        self.sum_all = 0.0

        word_counts_items = self.wc.word_counts.items()
            
        for other_word, counts in word_counts_items:
            for i in range(5):
                if self.a_list[i] == '_':
                    if other_word[i] in self.incorrect or other_word[i] in self.correct:
                        continue
                else:
                    if not other_word[i] == self.a_list[i]:
                        continue
                
                
            self.sum_all += self.wc.word_probability(other_word)

    def posterior_probability(self, word):
        if word not in self.wc.word_counts:
            return 0.0
        if self.num > 0:
            i = 0
            while (i < 5):
                if self.a_list[i] == '_':
                    if word[i] in self.incorrect or word[i] in self.correct:
                        return 0.0
                else:
                    if not word[i] == self.a_list[i]:
                        return 0.0
                i = i + 1

            answer = self.wc.word_probability(word) / self.sum_all
            return answer
        else:
            answer = self.wc.word_probability(word)
            return answer
            
    def print_state(self):
        print("current state: " + self.current)
        print("current incorrect: " + str(self.incorrect))

    def next_best_guess(self):
        best_guess = ('', 0.0)
        for letter in self.not_used:
            sum_prob = 0.0
            for (word, counts) in self.wc.word_counts.items():
                if letter in word:
                    sum_prob += self.posterior_probability(word) 

            if sum_prob > best_guess[1]:
                best_guess = (letter, sum_prob)
        return best_guess

if __name__ == "__main__":
    WordCounts = word_Counts()
    WordCounts.read_file("hw2_word_counts_05.txt")

    sorted_wc = WordCounts.counts_sorting()
    
    WordCounts.print_the_most()
    WordCounts.print_the_least()

    print("\n")
    Guess_Probability = GuessProbability(WordCounts, 0, '_', '_', '_', '_', '_', [], [])
    Guess_Probability.print_state()
    print(Guess_Probability.next_best_guess())

    print("\n")
    Guess_Probability_1 = GuessProbability(WordCounts, 2, '_', '_', '_', '_', '_', [], ['E', 'O'])
    print("Guessing...")
    Guess_Probability_1.print_state()
    print(Guess_Probability_1.next_best_guess())

    print("\n")
    Guess_Probability_2 = GuessProbability(WordCounts, 2, 'Q', '_', '_', '_', '_', ['Q'], [])
    print("Guessing...")
    Guess_Probability_2.print_state()
    print(Guess_Probability_2.next_best_guess())

    print("\n")
    Guess_Probability_3 = GuessProbability(WordCounts, 3, 'Q', '_', '_', '_', '_', ['Q'], ['U'])
    print("Guessing...")
    Guess_Probability_3.print_state()
    print(Guess_Probability_3.next_best_guess())

    print("\n")
    Guess_Probability_4 = GuessProbability(WordCounts, 5, '_', '_', 'Z', 'E', '_', ['Z', 'E'], ['A', 'D', 'I', 'R'])
    print("Guessing...")
    Guess_Probability_4.print_state()
    print(Guess_Probability_4.next_best_guess())

    print("\n")
    Guess_Probability_1 = GuessProbability(WordCounts, 2, '_', '_', '_', '_', '_', [], ['E', 'O'])
    print("Guessing...")
    Guess_Probability_1.print_state()
    print(Guess_Probability_1.next_best_guess())

    print("\n")
    Guess_Probability_2 = GuessProbability(WordCounts, 2, 'D', '_', '_', 'I', '_', ['D', 'I'], [])
    print("Guessing...")
    Guess_Probability_2.print_state()
    print(Guess_Probability_2.next_best_guess())

    print("\n")
    Guess_Probability_3 = GuessProbability(WordCounts, 3, 'D', '_', '_', 'I', '_', ['D', 'I'], ['A'])
    print("Guessing...")
    Guess_Probability_3.print_state()
    print(Guess_Probability_3.next_best_guess())

    print("\n")
    Guess_Probability_4 = GuessProbability(WordCounts, 6, '_', 'U', '_', '_', '_', ['U'], ['A', 'E', 'I', 'O', 'S'])
    print("Guessing...")
    Guess_Probability_4.print_state()
    print(Guess_Probability_4.next_best_guess())
