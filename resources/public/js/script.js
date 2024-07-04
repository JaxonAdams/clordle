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

const handleSubmitGuess = () => {};

const onPageLoad = () => {
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

                console.log(`SUBMITTING GUESS ${guess}`);
            }
        });
    });
};

document.addEventListener("DOMContentLoaded", onPageLoad);