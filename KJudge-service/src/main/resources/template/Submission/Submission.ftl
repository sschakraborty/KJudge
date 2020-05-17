<div class="card card-body mt-3 mb-3" id="app">
	<table class="table table-striped table-bordered">
		<thead>
			<tr>
				<th>Problem</th>
				<th>Submission Date-Time</th>
				<th>Result</th>
				<th>Language</th>
				<th>...</th>
			</tr>
		</thead>
		<tbody>
			<tr v-for="submission in model.submissionResult">
				<td>
					<a href="#problem" v-on:click="redirectToProblemPage(submission.problemHandle)">
						{{ submission.problemName }}
						<span class="badge badge-primary">
							{{ submission.problemHandle }}
						</span>
					</a>
				</td>
				<td>
					<code>
						{{ submission.dateTime.dayOfMonth }} {{ submission.dateTime.month }} {{ submission.dateTime.year }}
						({{ submission.dateTime.dayOfWeek }})
					</code>
					at
					<code>
						{{ submission.dateTime.hour }}:{{ submission.dateTime.minute }}:{{ submission.dateTime.second }}
					</code>
				</td>
				<td>
					{{ submission.outputCode }}
				</td>
				<td>
					{{ submission.language }}
				</td>
				<td>
					<a href="#expand" v-on:click="expand(submission)">
						<i class="fa fa-expand-arrows-alt"></i>
					</a>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" id="modal">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="card card-body m-3" v-if="selectedSubmission != null">
					<label>Source Code <span class="badge badge-primary">{{ selectedSubmission.language }}</span></label>
					<div class="alert alert-success">
						<code><pre class="m-0">{{ selectedSubmission.sourceCode }}</pre></code>
					</div>
					<label class="mt-2" v-if="hasErrorMessage">Error Dump</label>
					<div class="alert alert-warning" v-if="hasErrorMessage">
						<code><pre class="m-0">{{ selectedSubmission.compilationErrorMessage }}</pre></code>
					</div>
					<label class="mt-2">Details</label>
					<table class="table table-sm table-striped">
						<tr>
							<th>Compilation Error</th>
							<td>{{ selectedSubmission.isCompilationError }}</td>
						</tr>
						<tr>
							<th>Result (Output Code)</th>
							<td>{{ selectedSubmission.outputCode }}</td>
						</tr>
						<tr>
							<th>Language</th>
							<td>{{ selectedSubmission.language }}</td>
						</tr>
						<tr>
							<th>Problem Name</th>
							<td>{{ selectedSubmission.problemName }}</td>
						</tr>
						<tr>
							<th>Problem Handle</th>
							<td>{{ selectedSubmission.problemHandle }}</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	(function() {
		document.VueObject = new Vue({
			el: "#app",
			data: {
				"model": ${model},
				selectedSubmission: null
			},
			computed: {
				hasErrorMessage: function() {
					return this.selectedSubmission.compilationErrorMessage != null
						&& $.trim(this.selectedSubmission.compilationErrorMessage) != "";
				}
			},
			methods: {
				redirectToProblemPage: function(problemHandle) {
					location.href = "/problem/" + problemHandle;
				},
				expand: function(submission) {
					this.selectedSubmission = submission;
					$("#modal").modal({
						backdrop: true
					});
				}
			}
		});
	})();
</script>