package com.sschakraborty.platform.kjudge.security;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class TestMain {
	public static void main(String[] args) throws AbstractBusinessException {
		Vertx vertx = Vertx.vertx();
		GenericDAO genericDAO = new GenericDAO();
		Security security = new Security(vertx, genericDAO);
		HttpServer httpServer = vertx.createHttpServer();
		security.applyFilter();
		security.bindToServer(httpServer);
		httpServer.listen(9000);
	}
}