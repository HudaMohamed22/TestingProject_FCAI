*** Settings ***
Library           SeleniumLibrary
Library           Collections
Library           String
Library           re

*** Variables ***
${file_path}      E:/FCI/Level%204/FourthYear_Last%20Term/Software%20Testing/Assignments/ASS3/Assignment%203/Software-Testing-Assi3-Frontend/todo.html
${first_todo_title}    task1
${first_todo_desc}    it's a must
${second_todo_desc}    software testing assignment 3
${second_todo_title}    task2
${todo_num}       0

*** Test Cases ***
add_TODOs_test
    Open Local    E:/FCI/Level%204/FourthYear_Last%20Term/Software%20Testing/Assignments/ASS3/Assignment%203/Software-Testing-Assi3-Frontend/todo.html
    Input Text    //*[@id="todo"]    ${first_todo_title}
    Input Text    //*[@id="desc"]    ${first_todo_desc}
    sleep    1
    Click Element    //*[@id="todo-form"]/button
    ${input_txt}=    get value    //*[@id="todo"]
    ${input_txt2}=    get value    //*[@id="desc"]
    should be empty    ${input_txt}
    should be empty    ${input_txt2}
    sleep    2
    Input Text    //*[@id="todo"]    ${second_todo_title}
    Input Text    //*[@id="desc"]    ${second_todo_desc}
    sleep    1
    Click Element    //*[@id="todo-form"]/button
    ${input_txt}=    get value    //*[@id="todo"]
    ${input_txt2}=    get value    //*[@id="desc"]
    should be empty    ${input_txt}
    should be empty    ${input_txt2}
    ${title_list}    Get WebElements    xpath://tbody[@id='todo-table']//tr//td[2]
    ${text_list}=    Create List
    FOR    ${text_element}    IN    @{title_list}
        ${text}=    get text    ${text_element}
        Append To List    ${text_list}    ${text}
    END
    ${desc_list}    Get WebElements    xpath://tbody[@id='todo-table']//tr//td[3]
    ${desc_text_list}=    Create List
    FOR    ${text_element}    IN    @{desc_list}
        ${text}=    get text    ${text_element}
        Append To List    ${desc_text_list}    ${text}
    END
    log many    ${text_list}
    log many    ${desc_text_list}
    Should Contain    ${text_list}    ${first_todo_title}
    Should Contain    ${text_list}    ${second_todo_title}
    Should Contain    ${desc_text_list}    ${first_todo_desc}
    Should Contain    ${desc_text_list}    ${second_todo_desc}
    sleep    1
    Close Browser

mark_compelete_test
    Open Local    E:/FCI/Level%204/FourthYear_Last%20Term/Software%20Testing/Assignments/ASS3/Assignment%203/Software-Testing-Assi3-Frontend/todo.html
    ${rows_list}=    Get WebElements    xpath://tbody[@id='todo-table']//tr
    Should Be True    ${rows_list.__len__()} > 0
    sleep    1
    Checkbox Should Not Be Selected    //*[@id="checkbox-2"]
    Click Element    //*[@id="checkbox-2"]
    sleep    2
    Checkbox Should Be Selected    //*[@id="checkbox-2"]
    sleep    1
    Click Element    //*[@id="checkbox-2"]
    sleep    2
    Checkbox Should Not Be Selected    //*[@id="checkbox-2"]
    sleep    1
    Close Browser

get_completed _TODOs_test
    Open Local    E:/FCI/Level%204/FourthYear_Last%20Term/Software%20Testing/Assignments/ASS3/Assignment%203/Software-Testing-Assi3-Frontend/todo.html
    Maximize Browser Window
    Wait Until Element Is Visible    //*[@id="todo-form"]/h3    timeout=30s
    ${rows_list}=    Get WebElements    xpath://tbody[@id='todo-table']//tr
    Should Be True    ${rows_list.__len__()} > 0
    Click Element    xpath:/html/body/div/div/div[2]/button[2]
    sleep    2
    ${checkbox_list}=    Get WebElements    class:form-check-input
    FOR    ${ckeckbox}    IN    @{checkbox_list}
        Checkbox Should Be Selected    ${ckeckbox}
    END
    sleep    1
    Close Browser

Delete_test
    Open Local    E:/FCI/Level%204/FourthYear_Last%20Term/Software%20Testing/Assignments/ASS3/Assignment%203/Software-Testing-Assi3-Frontend/todo.html
    Maximize Browser Window
    Wait Until Element Is Visible    //*[@id="todo-form"]/h3    timeout=30s
    ${rows_list}=    Get WebElements    xpath://tbody[@id='todo-table']//tr
    Should Be True    ${rows_list.__len__()} > 0
    ${delete_buttons}=    Get WebElements    xpath://tbody[@id='todo-table']//tr//td//button
    ${pre_list_len}    Set Variable    ${rows_list.__len__()}
    Should Be True    ${rows_list.__len__()} > ${todo_num}
    ${delete_button}=    Set Variable    ${delete_buttons[${todo_num}]}
    ${element_id}=    Get Element Attribute    ${delete_button}    id
    ${delete_element}=    Get WebElements    xpath://tbody[@id='todo-table']//tr[2]//td[1]
    sleep    1
    Click Element    ${delete_button}
    sleep    1
    ${rows_list}=    Get WebElements    xpath://tbody[@id='todo-table']//tr
    Should Be True    ${rows_list.__len__()}+1 ==${pre_list_len}
    Page Should Not Contain Element    id=${element_id}
    sleep    1
    close browser

*** Keywords ***
Open Local
    [Arguments]    ${file_path}
    Open Browser    file://${file_path}    Chrome
