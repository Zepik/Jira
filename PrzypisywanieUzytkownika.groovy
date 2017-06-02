import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.event.type.EventDispatchOption
import com.atlassian.jira.user.ApplicationUser

IssueManager issueManager = ComponentAccessor.getIssueManager()

MutableIssue myissue = ComponentAccessor.getIssueManager().getIssueObject("PZ-1813")
def userManager = ComponentAccessor.userManager
def currentUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
log.error("Zalogowany: " + currentUser)

CustomField obszarZakupuCustomField = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectByName("Obszar zakupu")
def obszarZakpu = myissue.getCustomFieldValue(obszarZakupuCustomField)
log.error("ObszarZakupu: " + obszarZakpu)

if(obszarZakpu.toString() == "Kolizje i odkupienia sieci nn/SN")
{
    CustomField weryfikujacyCustomField = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectByName("Weryfikujący")
	ApplicationUser weryfikujacy = (ApplicationUser)myissue.getCustomFieldValue(weryfikujacyCustomField)
	log.error("Weryfikujacy: " + weryfikujacy.getName())
    
    def user = userManager.getUserByName(weryfikujacy.getName())
	myissue.setAssignee(user)
}
else
{
    myissue.setAssignee(myissue.reporter)
}

issueManager.updateIssue(currentUser, myissue, EventDispatchOption.ISSUE_UPDATED, true)
log.error("Ostatecznie: " + myissue.getAssignee())
