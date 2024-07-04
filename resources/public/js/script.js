// clordle client-side logic

const moveToNextInput = (nextGuessIdx, attemptIdx) => {
    if (nextGuessIdx >= 0 && nextGuessIdx <= 4) {
        const nextTargetId = `input#input-${nextGuessIdx}-${attemptIdx}`;
        const nextTarget = document.querySelector(nextTargetId);
        nextTarget.focus();
    }
};

const handleCharacterInput = event => {

    acceptedInputs = "abcdefghijklmnopqrstuvwxyz";

    const targetId = event.target.id;
    const targetIdParts = targetId.split("-");
    const attemptIdx = parseInt(targetIdParts[2]);
    const guessCharIdx = parseInt(targetIdParts[1]);

    if (acceptedInputs.indexOf(event.data) === -1) {
        event.target.value = "";
        return;
    }

    if (event.inputType === "insertText") {
        // navigate to next text box
        moveToNextInput(guessCharIdx + 1, attemptIdx);
    }
    // event.inputType === deleteContentBackward is handled by keydown event handler

};

const updateWithGuessResponse = (response, guessRow) => {
    const guessInputs = guessRow.querySelectorAll("input");

    for (let i = 0; i < response.length; i++) {
        const guessChar = guessInputs[i].value;

        guessInputs[i].classList.add("revealed");

        if (response[i][guessChar] === "in-position") {
            guessInputs[i].classList.add("in-position");
        } else if (response[i][guessChar] === "in-word") {
            guessInputs[i].classList.add("in-word");
        } else {
            guessInputs[i].classList.add("incorrect");
        }
    }
}

const handleSubmitGuess = async (guess, attemptIdx, guessRow) => {
    console.log(`SUBMITTING GUESS ${guess}`);

    const guessResponse = await fetch(`/api/check/${guess}`);
    if (!guessResponse.ok) {
        alert("Error submitting guess " + guess);
    }

    const guessResponseData = await guessResponse.json();
    
    console.log(guessResponseData);
    updateWithGuessResponse(guessResponseData, guessRow);

    guessRow.querySelectorAll("input").forEach(element => {
        element.setAttribute("disabled", true);
    });

    let isMatch = true;
    for (let resObj of guessResponseData) {
        if (Object.values(resObj)[0] != "in-position") {
            isMatch = false;
        }
    }

    if (isMatch) {
        setTimeout(() => {
            alert("Congratulations on guessing today's word! Come back tomorrow for a new word!");
        }, 500);
        return;
    }

    if (attemptIdx < 5) {
        // automatically move to the next row / guess
        nextRow = document.querySelector(`#input-row-${attemptIdx + 1}`);
        nextRow.querySelectorAll("input").forEach(element => {
            element.removeAttribute("disabled");
        });
        nextRow.querySelector(`#input-0-${attemptIdx + 1}`).focus();
    }

};

const onPageLoad = () => {
    const previousGuesses = new Set();

    const inputRows = document.querySelectorAll("div.input-row");

    // disable all but the first row
    for (let i = 1; i < inputRows.length; i++) {
        txtBoxes = inputRows[i].querySelectorAll("input");
        txtBoxes.forEach(element => {
            element.setAttribute("disabled", true);
        });
    }

    // set focus on first input text box
    document.querySelector("input#input-0-0").focus();

    // register event listeners for each of the input text boxes
    const allInput = document.querySelectorAll("input");
    allInput.forEach(element => {
        element.addEventListener("input", handleCharacterInput);
        element.addEventListener("keydown", event => {

            const targetId = event.target.id;
            const targetIdParts = targetId.split("-");
            const attemptIdx = parseInt(targetIdParts[2]);
            const guessCharIdx = parseInt(targetIdParts[1]);
            
            if (event.key === "Backspace") {
                if (guessCharIdx === 4 && event.target.value != "") {
                    event.target.value = "";
                } else {
                    moveToNextInput(guessCharIdx - 1, attemptIdx);
                }
            } else if (event.key === "Enter" && guessCharIdx === 4) {
                let guess = "";

                const guessRow = inputRows[attemptIdx];
                guessRow.querySelectorAll("input").forEach(element => {
                    guess += element.value;
                });

                if (!previousGuesses.has(guess)) {
                    previousGuesses.add(guess);
                    handleSubmitGuess(guess, attemptIdx, guessRow);
                    if (attemptIdx === 5) {
                        setTimeout(() => {
                            alert("Come back tomorrow for a new word!");
                        }, 500);
                    }
                } else {
                    console.log(`Already guessed ${guess}.`);
                }

            }
        });
    });
};

document.addEventListener("DOMContentLoaded", onPageLoad);