package com.sschakraborty.platform.kjudge.service.codec;

import com.sschakraborty.platform.kjudge.error.GenericException;
import com.sschakraborty.platform.kjudge.shared.model.CodingEvent;
import io.vertx.core.json.JsonObject;

public class CodingEventWithoutProblemsCodec implements Codec<CodingEvent, JsonObject> {
	@Override
	public JsonObject encode(CodingEvent object) throws GenericException {
		object.setProblemList(null);
		final JsonObject jsonObject = JsonObject.mapFrom(object);
		jsonObject.remove("problemList");
		return jsonObject;
	}

	@Override
	public CodingEvent decode(JsonObject object) {
		return object.mapTo(CodingEvent.class);
	}
}