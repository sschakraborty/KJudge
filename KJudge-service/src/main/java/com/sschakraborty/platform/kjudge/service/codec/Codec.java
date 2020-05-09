package com.sschakraborty.platform.kjudge.service.codec;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;

public interface Codec<X, Y> {
	Y encode(X object) throws AbstractBusinessException;

	X decode(Y object) throws AbstractBusinessException;
}