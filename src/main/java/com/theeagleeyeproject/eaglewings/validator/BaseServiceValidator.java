package com.theeagleeyeproject.eaglewings.validator;

/**
 * {@link BaseServiceValidator} validator interface that ensures the contract for all the service validators are the same.
 *
 * @param <I> generic type that references the service request
 * @author John Robert Martinez
 */
public interface BaseServiceValidator<I> {

    void validate(I request);
}
