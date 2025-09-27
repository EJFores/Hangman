import tkinter
from random_word import RandomWords

#root declaration, sizing, and random word generation
root = tkinter.Tk()
root.geometry("300x500")
r = RandomWords()
global random_word , mimic, attempt_counter
attempt_counter = 0
random_word = r.get_random_word()
mimic = "_" * len(random_word)

#logical functions
def reset_game(msg):
    global random_word, mimic, attempt_counter
    user_submit_display.config(text=msg)
    random_word = r.get_random_word()
    mimic = "_" * len(random_word)
    hint_label.config(text=mimic)
    attempt_counter = 0
    draw_gallows()

def mimic_word(random_word, user_char):
    global mimic
    for i in range(len(random_word)):
        if user_char.lower() == random_word[i].lower():
            mimic = mimic[:i] + random_word[i] + mimic[i+1:]
    return mimic

def check_submission(user_submission):
    if user_submission == random_word:
        return True
    elif len(user_submission) == 1 and user_submission.lower() in random_word.lower():
        return user_submission
    else:
        return False
    
def draw_gallows():
    canvas.delete("all")  # Clear the canvas
    # Draw the gallows
    canvas.create_line(50, 250, 200, 250, width=3)  # Base
    canvas.create_line(125, 250, 125, 50, width=3)  # Pole
    canvas.create_line(125, 50, 200, 50, width=3)   # Beam
    canvas.create_line(200, 50, 200, 75, width=3)   # Rope

def draw_hangman(stage):
    match stage:
        case 8:
            canvas.create_arc(188, 100, 212, 115, start=20, extent=140, style="arc", width=2) # Sad Mouth
        case 7:
            canvas.create_line(190, 85, 195, 90, width=2)   # Left Eye
            canvas.create_line(195, 85, 190, 90, width=2)
            canvas.create_line(205, 85, 210, 90, width=2)   # Right Eye
            canvas.create_line(210, 85, 205, 90, width=2)
        case 6:
            canvas.create_line(200, 175, 230, 225, width=3) # Right Leg
        case 5:
            canvas.create_line(200, 175, 170, 225, width=3) # Left Leg
        case 4:
            canvas.create_line(200, 125, 230, 150, width=3) # Right Arm
        case 3:
            canvas.create_line(200, 125, 170, 150, width=3) # Left Arm
        case 2:
            canvas.create_line(200, 115, 200, 175, width=3) # Body
        case 1:
            canvas.create_oval(180, 75, 220, 115, width=3)  # Head

    # Draw all previous stages as well
    for s in range(1, stage):
        draw_hangman(s)

# Modify display_text to update the hangman drawing
def display_text():
    global random_word, mimic, attempt_counter
    user_submission = text_box.get("1.0", "end-1c")
    results = check_submission(user_submission)
    if isinstance(results, bool):
        if results:
            reset_game("Correct! You guessed the word! Resetting...")
        else:
            user_submit_display.config(text="Incorrect guess.")
            attempt_counter += 1
    elif isinstance(results, str):
        user_submit_display.config(text=f"Correct letter: {results}")
        mimic = mimic_word(random_word, results)
        hint_label.config(text=mimic)

    if mimic == random_word:
        reset_game("You revealed the word! Resetting...")

    if attempt_counter >= 8:
        reset_game(f"Hanged! The word was '{random_word}'. Resetting...")

    draw_hangman(attempt_counter)

#widget declarations
canvas = tkinter.Canvas(root, width=300, height=300, bg="white")
draw_gallows()
text_box = tkinter.Text(root, height=1)
user_submit_button = tkinter.Button(root, text="Guess", command=display_text)
hint_label = tkinter.Label(root, text=mimic)
user_submit_display = tkinter.Label(root, text="")

#packing
canvas.pack()
text_box.pack()
user_submit_button.pack()
hint_label.pack()
user_submit_display.pack()

#finally main loop
root.mainloop()