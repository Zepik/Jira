import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.RendererManager

def comments = ComponentAccessor.commentManager.getComments(issue)
def baseurl = ComponentAccessor.getApplicationProperties().getString('jira.baseurl')
def issue_key = issue.key


def rendererManager = ComponentAccessor.getComponent(RendererManager.class)
def renderer = rendererManager.getRendererForType("atlassian-wiki-renderer")


if (comments)
{
    def link = "<a href=\""+baseurl+"/browse/"+issue_key+"?focusedCommentId="+comments.last().id+"&page=com.atlassian.jira.plugin.system.issuetabpanels:comment-tabpanel#comment-"+comments.last().id+"\">" +comments.last().getUpdated().format('MM/dd/yyyy H:mm:ss').toString() + " - " + comments.last().getAuthorFullName() +":"+ "</a>"
    def body = comments.last().body
    def result = link + renderer.render(body, null)
    return result
}
