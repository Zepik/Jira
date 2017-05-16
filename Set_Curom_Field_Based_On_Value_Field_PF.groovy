import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.MutableIssue

//IssueManager issueManager = ComponentManager.getInstance().getIssueManager();
//Issue issue = issueManager.getIssueObject("NMDT-88"); //
def customFieldManager = ComponentAccessor.getCustomFieldManager()
MutableIssue issue = ComponentAccessor.getIssueManager().getIssueObject("NMDT-88")
def issueType = issue.getIssueType().name

def Choice_Cf = customFieldManager.getCustomFieldObjectByName("Field Name")
def Choice_Cf_value = Choice_Cf.getValue(issue)

if(issueType == "Task")
{
    switch (Choice_Cf_value) {
        case "test": return "30"; break;
        //...
    }

}
}
