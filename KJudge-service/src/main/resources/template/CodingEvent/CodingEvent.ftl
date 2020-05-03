<div id="app">
	<section v-if="codingEvent == null">
		<div class="row mt-3">
			<div class="col-4"></div>
			<div class="col-4">
				<button class="btn btn-success btn-block btn-sm" v-on:click="newEvent">Create New Event</button>
			</div>
			<div class="col-4"></div>
		</div>
	</section>
	<section v-if="codingEvent == null">
		<div class="row mt-3">
			<div class="col">
				<div class="row">
					<div class="col-6" v-for="event in model.allCodingEvents">
						<div class="card">
							<div class="card-body">
								<h5 class="card-title">{{event.eventName}}</h5>
								<h6 class="card-subtitle mb-2 text-muted">{{event.eventHandle}}
								</h6>
								<p class="card-text" v-html="markdownConverter.makeHtml(event.description)"></p>
								<p class="card-text">
									Starts
									<code>
										{{String(new Date(event.startTime))}}
									</code>
									and ends
									<code>
										{{String(new Date(event.endTime))}}
									</code>
								</p>
								<a class="card-link" href="#">Go to Event</a>
								<a class="card-link btn-btn-sm" href="#" v-on:click="codingEvent = event">Edit</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section v-if="codingEvent != null">
		<div class="row mt-3">
			<div class="col-8">
				<div class="card">
					<div class="card-header bg-info text-white">
						Coding Event Details
					</div>
					<div class="card-body" style="background-color: #F0F0F0;">
						<div class="row">
							<div class="col">
								<label>Event Handle (ID)</label>
								<input class="form-control form-control-sm" maxlength="25" placeholder="#_handle"
								       type="text" v-model="codingEvent.eventHandle"/>
							</div>
						</div>
						<div class="row mt-3">
							<div class="col">
								<label>Event Type</label>
								<select class="form-control form-control-sm" v-model="codingEvent.eventType">
									<option value="CONTEST">Contest</option>
									<option value="DAILY_CHALLENGE">Daily Challenge</option>
									<option value="WEEKLY_CHALLENGE">Weekly Challenge</option>
									<option value="MONTHLY_CHALLENGE">Monthly Challenge</option>
									<option value="Hackathon">Hackathon</option>
								</select>
							</div>
							<div class="col">
								<label>Participation Type</label>
								<select class="form-control form-control-sm" v-model="codingEvent.participationType">
									<option value="SYNCHRONIZED_GLOBAL">Global Start / End</option>
									<option value="FREE_GLOBAL">Individual Start / End</option>
									<option value="FREE_LOCAL">No Restrictions</option>
								</select>
							</div>
						</div>
						<div class="row mt-3">
							<div class="col">
								<label>Event Name</label>
								<input class="form-control form-control-sm" maxlength="50" placeholder="#_name"
								       type="text" v-model="codingEvent.eventName"/>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-4">
				<div class="card btn-block">
					<div class="card-header bg-info text-white">
						Other Details
					</div>
					<div class="card-body" style="background-color: #F0F0F0;">
						<div class="row">
							<div class="col">
								<label>Start Date-Time</label>
								<input class="form-control form-control-sm" type="text"
								       v-model="codingEvent.startTime"/>
							</div>
						</div>
						<div class="row mt-3">
							<div class="col">
								<label>End Date-Time</label>
								<input class="form-control form-control-sm" type="text" v-model="codingEvent.endTime"/>
							</div>
						</div>
						<div class="row mt-3">
							<div class="col">
								Starts
								<code>
									{{String(new Date(codingEvent.startTime))}}
								</code>
								and ends
								<code>
									{{String(new Date(codingEvent.endTime))}}
								</code>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row mt-3">
			<div class="col">
				<div class="card btn-block">
					<div class="card-header bg-info text-white">
						Description
					</div>
					<div class="card-body" style="background-color: #F0F0F0;">
						<div class="row">
							<div class="col">
								<label>Description <span class="badge badge-success">MARKDOWN</span></label>
								<textarea class="form-control form-control-sm" rows="15"
								          v-model="codingEvent.description"></textarea>
							</div>
							<div class="col">
								<label>Description Preview <span class="badge badge-secondary">Actual</span></label>
								<div class="card card-body" v-html="renderedDescription"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row mt-3 mb-3">
			<div class="col-3"></div>
			<div class="col-6">
				<div class="card btn-block">
					<div class="card-body" style="background-color: #F0F0F0;">
						<div class="row">
							<div class="col">
								<button class="btn btn-sm btn-success btn-block" v-on:click="sendUpdate">Create / Update
									Event
								</button>
							</div>
							<div class="col">
								<button class="btn btn-sm btn-danger btn-block" v-on:click="clearUpdate">Cancel</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-3"></div>
		</div>
	</section>
	<form id="CodingEventUpdateForm" method="POST" style="display: none !important;">
		<input name="model" type="hidden" v-model="JSON.stringify(codingEvent)"/>
	</form>
</div>
<script>
	(function() {
		new Vue({
			el: "#app",
			data: {
				"markdownConverter": new showdown.Converter(),
				"codingEvent": null,
				"model": ${model}
			},
			computed: {
				renderedDescription: function() {
					return this.markdownConverter.makeHtml(this.codingEvent.description);
				}
			},
			methods: {
				sendUpdate: function() {
					$("form#CodingEventUpdateForm").submit();
				},
				clearUpdate: function() {
					this.codingEvent = null;
				},
				newEvent: function() {
					this.codingEvent = {
						"eventHandle" : "",
					    "eventType" : "CONTEST",
					    "participationType" : "FREE_GLOBAL",
					    "eventName" : "",
					    "description" : "## Write a description here",
					    "startTime" : "2020-05-03T08:00:00",
					    "endTime" : "2020-05-03T12:00:00"
					};
				}
			}
		});
	})();

</script>