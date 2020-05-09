<div id="app">
	<div class="row mt-3">
		<div class="col">
			<div class="card card-body">
				<p>
				<h2>{{ model.codingEvent.eventName }}</h2>
				<h5>
					<code>
						{{ model.codingEvent.eventHandle }}
					</code>
				</h5>
				</p>
				<p>
				<h5>Starts @
					<code>
						{{String(new Date(model.codingEvent.startTime))}}
					</code>
				</h5>
				<h5>
					Ends @
					<code>
						{{String(new Date(model.codingEvent.endTime))}}
					</code>
				</h5>
				</p>
			</div>
		</div>
		<div class="col">
			<div class="card card-body">
				<p v-html="renderedDescription"></p>
			</div>
		</div>
	</div>
	<div class="row mt-3">
		<div class="col">
			<div class="card">
				<div class="card-header">Problems</div>
				<div class="card-body">
					<div class="row">
						<div class="col-6" v-for="problem in model.codingEvent.problemList">
							<div class="card card-body">
								<h5 class="card-title">{{ problem.name }} <span class="badge badge-primary">{{ problem.problemHandle }}</span>
								</h5>
								<p class="card-text" v-html="renderHtml(problem.description)"></p>
								<a class="card-link" href="#" v-on:click="goToProblem(problem.problemHandle)">SOLVE</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	(function() {
		new Vue({
			el: "#app",
			data: {
				"markdownConverter": new showdown.Converter(),
				"model": ${model}
			},
			computed: {
				renderedDescription: function() {
					return this.markdownConverter.makeHtml(this.model.codingEvent.description);
				}
			},
			methods: {
				renderHtml: function(data) {
					return this.markdownConverter.makeHtml(data);
				},
				goToProblem: function(handle) {
					window.location.href = "/problem/" + handle;
				}
			}
		});
	})();


</script>