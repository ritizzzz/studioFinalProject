let dropdownOptions = {
    "LTHC": ["Code", "Name", "Indigenous Status", "Sex", "Condition", "Count"],
    "schoolCompletion":  ["Code", "Name", "Indigenous Status", "Sex", "School Year", "Count"],
    "weeklyIncome": ["Code", "Name", "Indigenous Status", "Income Bracket", "Count"],
    "populationStatistics": ["Code", "Name", "Indigenous Status", "Sex", "Age Category", "Count"]
};

let dropdownOptionsValue = {
    "LTHC": ["code", "lgaName", "indigenousStatus", "sex", "condition", "count"],
    "schoolCompletion":  ["code", "lgaName", "indigenousStatus", "sex", "schoolYear", "count"],
    "weeklyIncome": ["code", "lgaName", "indigenousStatus", "incomeBracket", "count"],
    "populationStatistics": ["code", "lgaName", "indigenousStatus", "sex", "ageCategory", "count"]
};

let outcomeDropdown = document.querySelector("#outcomeDropdown");
let columnDropdown = document.querySelector("#columnDropdown");


outcomeDropdown.addEventListener("change", ()=>{
    columnDropdown.length = 0;
    let columnValue = dropdownOptions[outcomeDropdown.value];
    let optionsValue = dropdownOptionsValue[outcomeDropdown.value];
    for(let i = 0; i<columnValue.length; i++){
        columnDropdown.options[columnDropdown.options.length] = new Option(columnValue[i], optionsValue[i]);
    }

});

