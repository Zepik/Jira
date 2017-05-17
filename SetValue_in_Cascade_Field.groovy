import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.fields.option.Option
import com.atlassian.jira.issue.ModifiedValue
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder
import com.atlassian.jira.issue.customfields.manager.OptionsManager

//MutableIssue issue = ComponentAccessor.getIssueManager().getIssueObject("PA-69") //Optional, useful for testing in script console

CustomField Spolka_komorka_cf = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectByName("Spółka/Komórka (Zgłaszający)"); /cascade field with values
CustomField komorka = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectByName("Komórka");  /text field
String komorka_value = komorka.getValue(issue)

def If_value_is_correct;
String allOptions = [];
Long komorka_do_uzupelnienia;
Long spolka_do_uzupelnienia
def fieldConfig = Spolka_komorka_cf.getRelevantConfig(issue) 
OptionsManager optionsManager = ComponentAccessor.getOptionsManager();
def options = optionsManager.getOptions(fieldConfig)


for (def i = 0; i <options.size(); i++) { //iterante trgouht parent options
    options[i].childOptions.toList().each { //get all child options 
        if(it.toString() == komorka_value) //check if value in text field exist in cascade field
        {
            komorka_do_uzupelnienia = it.getOptionId()
            spolka_do_uzupelnienia = it.parentOption.getOptionId()
            If_value_is_correct = true
        }
    }
    if(If_value_is_correct)
    {
        def parentOptionObj = optionsManager.findByOptionId(spolka_do_uzupelnienia); // value to put in first list
        def childOptionObj = optionsManager.findByOptionId(komorka_do_uzupelnienia);  // value to put in second list
        Map<String,Object> newValues = new HashMap<String, Object>();
        newValues.put(null, parentOptionObj);
        newValues.put("1", childOptionObj);
        Spolka_komorka_cf.updateValue(null, issue, new ModifiedValue(null, newValues), new DefaultIssueChangeHolder()); //set Custom Field value

    }


}

