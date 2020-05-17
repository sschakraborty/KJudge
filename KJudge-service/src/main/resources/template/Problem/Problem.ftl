<div id="app">
	<div class="row mt-3">
		<div class="col-9">
			<div class="card">
				<div class="card-header">
					{{ model.problem.name }} <span class="badge badge-primary">{{ model.problem.problemHandle }}</span>
				</div>
				<div class="card-body">
					<p v-html="renderedDescription"></p>
					<div class="row">
						<div class="col">
							<table class="table table-striped table-bordered table-sm">
								<thead>
								<tr>
									<th colspan="2">Time Constraints</th>
								</tr>
								<tr v-if="model.problem.timeConstraint.description != null && model.problem.timeConstraint.description != ''">
									<th colspan="2"
									    v-html="renderDescription(model.problem.timeConstraint.description)">
									</th>
								</tr>
								</thead>
								<tbody>
								<tr v-for="key in Object.keys(model.problem.timeConstraint.timeConstraints)">
									<th>{{ key }}</th>
									<td>{{ model.problem.timeConstraint.timeConstraints[key] }} ms</td>
								</tr>
								</tbody>
							</table>
						</div>
						<div class="col">
							<table class="table table-striped table-bordered table-sm">
								<thead>
								<tr>
									<th colspan="2">Memory Constraints</th>
								</tr>
								<tr v-if="model.problem.memoryConstraint.description != null && model.problem.memoryConstraint.description != ''">
									<th colspan="2"
									    v-html="renderDescription(model.problem.memoryConstraint.description)">
									</th>
								</tr>
								</thead>
								<tbody>
								<tr v-for="key in Object.keys(model.problem.memoryConstraint.memoryConstraints)">
									<th>{{ key }}</th>
									<td>{{ model.problem.memoryConstraint.memoryConstraints[key] }} kB</td>
								</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-3">
			<label>Editor Syntax Highlighting</label>
			<select class="form-control form-control-sm" v-model="cEditor_mode" v-on:change="updateEditor">
				<option v-for="entry in Object.keys(CodeMirror.mimeModes)">{{ entry }}</option>
			</select>

			<label class="mt-3">Submission Language</label>
			<select class="form-control form-control-sm" v-model="language">
				<option v-for="entry in model.languages">{{ entry }}</option>
			</select>

			<p class="mt-3">
				<code>CTRL + Space will trigger autocomplete in code editor!</code>
			</p>
		</div>
		<div class="col-12 mt-3">
			<div class="row">
				<div class="col-5">
					<textarea class="form-control form-control-sm" rows="5"></textarea>
				</div>
				<div class="col-5">
					<textarea class="form-control form-control-sm" rows="5"></textarea>
				</div>
				<div class="col-2">
					<button class="btn btn-info btn-block btn-sm" v-on:click="mockSubmitCode">Run Code</button>
					<button class="btn btn-success btn-block btn-sm">SUBMIT</button>
				</div>
			</div>
		</div>
		<div class="col-12 mt-3">
			<textarea id="code-space"></textarea>
		</div>
	</div>
</div>
<script>
	(function() {
		$("<link/>", {
			rel: "stylesheet",
			type: "text/css",
			href: "/resources/codemirror/lib/codemirror.css"
		}).appendTo("head");

		$("<link/>", {
			rel: "stylesheet",
			type: "text/css",
			href: "/resources/codemirror/addon/fold/foldgutter.css"
		}).appendTo("head");

		$("<link/>", {
			rel: "stylesheet",
			type: "text/css",
			href: "/resources/codemirror/addon/hint/show-hint.css"
		}).appendTo("head");

		var script_arr = [
		    'lib/codemirror.js',
		    'mode/python/python.js',
		    'mode/clike/clike.js',
		    'addon/fold/foldcode.js',
		    'addon/fold/brace-fold.js',
		    'addon/fold/comment-fold.js',
		    'addon/fold/indent-fold.js',
		    'addon/fold/foldgutter.js',
		    'addon/hint/show-hint.js',
		    'addon/hint/anyword-hint.js',
		    'addon/hint/javascript-hint.js'
		];

		$.getMultiScripts(script_arr, '/resources/codemirror/').done(function() {
		    document.VueObject = new Vue({
				el: "#app",
				data: {
					"sourceCode": "//## Type your code here ##//",
					"language": "",
					"cEditor_mode": "text/x-csrc",
					"markdownConverter": new showdown.Converter(),
					"model": ${model}
				},
				computed: {
					renderedDescription: function() {
						return this.markdownConverter.makeHtml(this.model.problem.description);
					}
				},
				methods: {
					updateEditor: function() {
						document.cEditor.setOption("mode", this.cEditor_mode)
					},
					mockSubmitCode: function() {
						var that = this;
						const modelJSON = JSON.stringify({
							codeSubmission: {
								language: that.language,
								sourceCode: that.sourceCode
							},
							problemHandle: that.model.problem.problemHandle
						});
						$.post("/submission/mockSubmit", { model: modelJSON }, function(data) {
							location.href = "/submission";
						});
					}
				},
				mounted: function() {
					document.cEditor = CodeMirror.fromTextArea(document.getElementById("code-space"), {
						lineNumbers: true,
						mode: "text/x-csrc",
						indentUnit: 4,
						foldGutter: true,
						gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"],
						extraKeys: {"Ctrl-Space": "autocomplete"}
					});
					document.cEditor.on("changes", () => {
						this.sourceCode = document.cEditor.getValue();
					});
					document.cEditor.setValue(this.sourceCode);
				}
			});
		});
	})();
</script>
<style>
	.CodeMirror {
		border: 1px solid #eee;
		height: auto;
	}
</style>