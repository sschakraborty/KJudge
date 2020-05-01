<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"
	      name="viewport">
	<meta content="ie=edge" http-equiv="X-UA-Compatible">
	<title>Login - Security</title>
	<link href="/sso/auth/resource/chota.min.css" rel="stylesheet"/>
	<style>
		html, body {
			padding: 0px;
			margin: 0px;
			overflow: hidden !important;
		}
	</style>
</head>
<body>
<div class="row" style="margin-top: 75px;">
	<div class="col-3"></div>
	<div class="col-6 card">
		<div class="text-center">
			<h1>KSystem Judge</h1>
		</div>

		<form action="${currentURL}" method="post">
			<fieldset>
				<legend>SIGN IN</legend>
				<div class="row">
					<div class="col">
						<div class="row">
							<div class="col">
								<label>Username</label>
								<input name="principal" placeholder="#_username" type="text"/>
							</div>
						</div>
						<div class="row">
							<div class="col">
								<label>Password</label>
								<input name="password" placeholder="#_password" type="password"/>
							</div>
						</div>
						<div class="row">
							<div class="col text-center">
								<button type="submit">SIGN IN</button>
								<button class="button clear">Forgot Password?</button>
							</div>
						</div>
					</div>
				</div>
			</fieldset>
		</form>

		<div class="text-center">
			<h4>${message}</h4>
		</div>
		<div class="col-3"></div>
	</div>
</div>
</body>
</html>