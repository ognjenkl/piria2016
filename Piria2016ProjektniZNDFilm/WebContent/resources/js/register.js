$(function validateRegisterForm() {
	$("#registerForm\\:registerBtn").click(function() {		
//		if($("#registerForm\\:usernameRegister").val().length > 2 )
//			return true;
//		alert("PRIJE " + $("#registerForm\\:usernameRegister").val());
//		console.log($("#registerForm\\:usernameRegister").val().search("^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?![#@\/])[\w\d.~!$%^&_\-=]{5,}$"));
//		alert($("#registerForm\\:usernameRegister").val().search("^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?![#@\/])[\w\d.~!$%^&_\-=]{5,}$"));
//		var str = "nesto";
		//alert(str.search("/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?![#@\/])[\w\d.~!$%^&_\-=]{5,}$"));
		
		alert("hm");
		//alert(str.search("e"));
		var str = $("#registerForm\\:usernameRegister").val();
		alert(str);
		alert(str.search("/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?![#@\/])[\w\d.~!$%^&_\-=]{5,}$"));
		var value = str.search("/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?![#@\/])[\w\d.~!$%^&_\-=]{5,}$");
		alert(value);
		alert("pred if");
		if($("#registerForm\\:usernameRegister").val().search("^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?![#@\/])[\w\d.~!$%^&_\-=]{5,}$") >= 0){
			alert("duzina: " + $("#registerForm\\:usernameRegister").val());
			return true;
		}
		 
		alert("Morate da unesete ispravne podatke za registraciju");
		return false;
	});
});