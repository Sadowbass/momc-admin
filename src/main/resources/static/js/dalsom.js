let setDateValue = (target) => {
    let inputDate = target.querySelector("input[type=date]");

    function createFormattedDate() {
        const leftPad = (value) => {
            if (value >= 10) {
                return value;
            }
            return `0${value}`;
        }

        let date = new Date();
        let year = date.getFullYear();
        let month = leftPad(date.getMonth() + 1);
        let day = leftPad(date.getDate());

        return [year, month, day].join("-");
    }

    inputDate.value = createFormattedDate();
}