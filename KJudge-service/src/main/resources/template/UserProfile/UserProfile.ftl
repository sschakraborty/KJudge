Hello ${userProfile.firstName} ${userProfile.lastName}!
<#list userProfile.emails as email>
${email}<br>
</#list>