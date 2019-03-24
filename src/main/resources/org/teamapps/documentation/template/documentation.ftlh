<#-- @ftlvariable name="class" type="org.teamapps.documentation.generator.model.DocClass" -->
<!DOCTYPE html>
<html lang="en">
<head>
	<title>${(class.title)!"TeamApps Documentation"}</title>
</head>
<body>
<#if class.title??><h1>${class.title}</h1></#if>

<#list class.docMethods as method>
	<h2 class="method-doc-title">${method.title}</h2>
	<#if method.javaDoc??>
		<div class="method-doc-text">
			<#noautoesc>
${method.javaDoc}
			</#noautoesc>
		</div>
	</#if>

	<pre class="method-doc-code">
		<#if method.includeMethodDeclaration>
			<#noautoesc>
${method.fullMethodCode}
			</#noautoesc>
		<#else>
			<#noautoesc>
${method.fullMethodCode}
			</#noautoesc>
		</#if>
	</pre>

</#list>

</body>
</html>