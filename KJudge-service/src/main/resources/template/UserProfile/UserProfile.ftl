<div class="row mt-3" id="app">
	<form action="./" id="mainForm" method="POST" style="display: none !important;">
		<input name="model" type="hidden" v-model="modelJSON"/>
	</form>
	<div class="col-3"></div>
	<div class="col-6">
		<div class="card">
			<div class="card-header">
				Profile
			</div>
			<div class="card-body">
				<div class="row">
					<div class="col">
						<label>First Name</label>
						<input class="form-control form-control-sm" placeholder="#_first_name" type="text"
						       v-model="model.userProfile.firstName">
					</div>
				</div>
				<div class="row mt-3">
					<div class="col">
						<label>Middle Name</label>
						<input class="form-control form-control-sm" placeholder="#_middle_name" type="text"
						       v-model="model.userProfile.middleName">
					</div>
				</div>
				<div class="row mt-3">
					<div class="col">
						<label>Surname</label>
						<input class="form-control form-control-sm" placeholder="#_last_name" type="text"
						       v-model="model.userProfile.lastName">
					</div>
				</div>
				<div class="row mt-3">
					<div class="col">
						<label>Display Name</label>
						<input class="form-control form-control-sm" placeholder="#_display_name" type="text"
						       v-model="model.userProfile.displayName">
					</div>
				</div>
				<div class="row mt-3">
					<div class="col-4">
						<label>e-Mail IDs</label>
					</div>
					<div class="col-8">
						<div class="row" v-for="email in model.userProfile.emails">
							<div class="col">
								<input class="form-control form-control-sm" placeholder="#_email" v-model="email">
							</div>
						</div>
					</div>
				</div>
				<div class="row mt-3">
					<div class="col-4">
						<label>Phone Numbers</label>
					</div>
					<div class="col-8">
						<div class="row" v-for="phone in model.userProfile.phoneNumbers">
							<div class="col">
								<input class="form-control form-control-sm" placeholder="#_phone" v-model="phone">
							</div>
						</div>
					</div>
				</div>
				<div class="row mt-3">
					<div class="col"></div>
					<div class="col">
						<button class="btn btn-sm btn-block btn-success" v-on:click="submit">UPDATE</button>
					</div>
					<div class="col"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-3"></div>
</div>
<script>
	(function() {
		new Vue({
			el: "#app",
			data: {
				model: ${model}
			},
			computed: {
				modelJSON: function() {
					return JSON.stringify(this.model);
				}
			},
			methods: {
				submit: function() {
					$("#app #mainForm").submit();
				}
			}
		});
	})();

</script>