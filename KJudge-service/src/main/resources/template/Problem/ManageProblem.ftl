<div id="app">
	<form id="createProblemFormData" method="POST" style="display: none;">
		<input name="model" type="hidden" v-model="formData"/>
	</form>
	<div class="row mt-3">
		<div class="card card-body" style="background-color: #F0F0F0;">
			<div class="card-title"><h3>Problem Details</h3></div>
			<div class="card-text">
				<div class="row">
					<div class="col">
						<label>Problem Handle (ID)</label>
						<input class="form-control form-control-sm" placeholder="#_problem_handle" type="text"
						       v-model="creationData.problem.problemHandle"/>
					</div>
					<div class="col">
						<label>Coding Event Handle</label>
						<input class="form-control form-control-sm" placeholder="#_coding_event" type="text"
						       v-model="creationData.problem.codingEventHandle"/>
					</div>
				</div>

				<div class="row mt-3">
					<div class="col-3"></div>
					<div class="col-6">
						<label>Name</label>
						<input class="form-control form-control-sm" placeholder="#_name" type="text"
						       v-model="creationData.problem.name"/>
					</div>
					<div class="col-3"></div>
				</div>

				<div class="row mt-3">
					<div class="col-6">
						<label>Description <span class="badge badge-primary">LaTeX</span></label>
						<textarea class="form-control form-control-sm" rows="10"
						          v-model="creationData.problem.description"></textarea>
					</div>
					<div class="col-6">
						<label>Preview <span class="badge badge-primary">LaTeX</span></label>
						<div class="card card-body" v-html="renderedLatex"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row mt-3">
		<div class="card card-body" style="background-color: #F0F0F0;">
			<div class="card-title"><h3>Testcases</h3></div>
			<table class="table table-condensed table-bordered">
				<tr>
					<th>Testcase Name</th>
					<th>Input File Path</th>
					<th>Expected Output File Path</th>
					<th>...</th>
				</tr>
				<tr v-for="(testcase, index) in creationData.testcases">
					<td>
						<input class="form-control form-control-sm" placeholder="#_name" type="text"
						       v-model="testcase.name"/>
					</td>
					<td>
						<input class="form-control form-control-sm" placeholder="#_input_file_path" type="text"
						       v-model="testcase.inputFilePath"/>
					</td>
					<td>
						<input class="form-control form-control-sm" placeholder="#_expected_output_file_path"
						       type="text" v-model="testcase.expectedOutputFilePath"/>
					</td>
					<td>
						<button class="btn btn-danger btn-sm" v-on:click="deleteTestcase(index)">Delete</button>
					</td>
				</tr>
				<tr>
					<th class="text-right" colspan="4">
						<button class="btn btn-success btn-sm" v-on:click="newTestcase">Add Testcase</button>
					</th>
				</tr>
			</table>
		</div>
	</div>
	<div class="row mt-3">
		<div class="card card-body" style="background-color: #F0F0F0;">
			<div class="card-title"><h3>Solution</h3></div>
			<table class="table table-condensed table-bordered">
				<tr>
					<th>Source Code</th>
					<th>Language</th>
					<th>...</th>
				</tr>
				<tr v-for="(solution, index) in creationData.solutions">
					<td>
						<textarea class="form-control form-control-sm" v-model="solution.sourceCode"></textarea>
					</td>
					<td>
						<select class="form-control form-control-sm" v-model="solution.language">
							<option v-for="lang in model.languages">{{ lang }}</option>
						</select>
					</td>
					<td>
						<button class="btn btn-danger btn-sm" v-on:click="deleteSolution(index)">Delete</button>
					</td>
				</tr>
				<tr>
					<th class="text-right" colspan="4">
						<button class="btn btn-success btn-sm" v-on:click="newSolution">Add Solution</button>
					</th>
				</tr>
			</table>
		</div>
	</div>
	<div class="row mt-3" v-if="mapViewer != null">
		<div class="card card-body" style="background-color: #F0F0F0;">
			<div class="card-title">
				<h3 class="float-left">{{ mapViewer.title }}</h3>
				<div class="float-right" style="display: inline !important;">
					<select class="form-control" v-model="mapViewer">
						<option v-bind:value="mv" v-for="mv in mapViewerMetadata">{{ mv.title }}</option>
					</select>
				</div>
			</div>
			<table class="table table-condensed table-bordered">
				<tr v-for="key in Object.keys(mapViewer.map)">
					<th>
						{{ key }}
					</th>
					<td>
						<input class="form-control form-control-sm" placeholder="#_expected_output_file_path"
						       type="text"
						       v-model="mapViewer.map[key]"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="row mt-3 mb-3">
		<div class="col-3"></div>
		<div class="col-6 card card-body text-center" style="background-color: #F0F0F0;">
			<button class="btn btn-sm btn-success" v-on:click="createProblemAction">CREATE PROBLEM</button>
		</div>
		<div class="col-3"></div>
	</div>
</div>
<script>
	(function() {
		new Vue({
			el: "#app",
			data: {
				"model": ${model},
				"creationData": {
					"problem": {
						problemHandle: "",
						codingEventHandle: "",
						name: "",
						description: ""
					},
					testcases: [],
					solutions: [],
					ioConstraints: {},
					timeConstraints: {},
					memoryConstraints: {},
				},
				mapViewer: null,
				mapViewerMetadata: [
				]
			},
			computed: {
				renderedLatex: function() {
					return katex.renderToString(this.creationData.problem.description, {
						throwOnError: false
					});
				},
				formData: function() {
					return JSON.stringify({ "creationData": this.creationData });
				}
			},
			methods: {
				newTestcase: function() {
					this.creationData.testcases.push({
						name: "",
						inputFilePath: "",
						expectedOutputFilePath: ""
					});
				},
				deleteTestcase: function(index) {
					this.creationData.testcases.splice(index, 1);
				},
				newSolution: function() {
					this.creationData.solutions.push({
						language: "",
						sourceCode: ""
					});
				},
				deleteSolution: function(index) {
					this.creationData.solutions.splice(index, 1);
				},
				createProblemAction: function() {
					$("form#createProblemFormData").submit();
				}
			},
			created() {
				var that = this;
				this.model.languages.forEach(function(lang) {
					that.creationData.ioConstraints[lang] = 10000;
					that.creationData.timeConstraints[lang] = 2000;
					that.creationData.memoryConstraints[lang] = 100000;
				});
				this.mapViewerMetadata.push({
					title: "Time Constraints",
					map: this.creationData.timeConstraints
				});
				this.mapViewerMetadata.push({
					title: "IO Constraints",
					map: this.creationData.ioConstraints
				});
				this.mapViewerMetadata.push({
					title: "Memory Constraints",
					map: this.creationData.memoryConstraints
				});
				this.mapViewer = this.mapViewerMetadata[0];
			}
		});
	})();


</script>