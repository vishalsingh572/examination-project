<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<form method="post" action="/demo/upload" enctype="multipart/form-data">
	<input type="text" name="name" /> <input type="file" name="file" /> <input
		type="submit" />
</form>