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
	<#list method.images as image>
		${image}
		<img src="${image}" alt="${image}"/>
	</#list>
	<#if method.javaDoc??>
		<div class="method-doc-text">
			<#noautoesc>
				${method.javaDoc}
			</#noautoesc>
		</div>
	</#if>

	<pre class="method-doc-code"><code><#if method.includeMethodDeclaration><#noautoesc>${method.fullMethodCode}</#noautoesc><#else><#noautoesc>${method.bodyCode}</#noautoesc></#if></code></pre>

</#list>


<style>
	code {
		tab-size: 4ch;
	}
</style>
<link rel="stylesheet"
	  href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@9.15.6/build/styles/default.min.css">
<script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@9.15.6/build/highlight.min.js"></script>
<script>
	hljs.configure({
		languages: ['java']
	});
	document.querySelectorAll('pre code').forEach((block) => {
		hljs.highlightBlock(block);
	})
</script>
</body>
</html>