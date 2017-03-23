/*
 * Copyright 2017 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.gaffer.store.operation.handler.output;

import org.junit.Test;
import uk.gov.gchq.gaffer.commonutil.iterable.WrappedCloseableIterable;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.impl.output.ToArray;
import uk.gov.gchq.gaffer.store.Context;
import java.util.Arrays;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ToArrayHandlerTest {

    @Test
    public void shouldConvertIterableToArray() throws OperationException {
        // Given
        final Integer[] originalArray = new Integer[]{1, 2, 3};

        final Iterable<Integer> originalResults = new WrappedCloseableIterable<>(Arrays.asList(originalArray));
        final ToArrayHandler<Integer> handler = new ToArrayHandler();
        final ToArray operation = mock(ToArray.class);

        given(operation.getInput()).willReturn(originalResults);

        //When
        final Integer[] results = handler.doOperation(operation, new Context(), null);

        //Then
        assertArrayEquals(originalArray, results);
    }

    @Test
    public void shouldHandleNullInput() throws OperationException {
        // Given
        final ToArrayHandler<Integer> handler = new ToArrayHandler();
        final ToArray operation = mock(ToArray.class);

        given(operation.getInput()).willReturn(null);

        //When
        final Integer[] results = handler.doOperation(operation, new Context(), null);

        //Then
        assertThat(results, is(nullValue()));
    }

    @Test
    public void shouldHandleZeroLengthInput() throws OperationException {
        // Given
        final Integer[] originalArray = new Integer[]{};

        final Iterable<Integer> originalResults = new WrappedCloseableIterable<>(Arrays.asList(originalArray));
        final ToArrayHandler<Integer> handler = new ToArrayHandler();
        final ToArray operation = mock(ToArray.class);

        given(operation.getInput()).willReturn(originalResults);

        //When
        final Integer[] results = handler.doOperation(operation, new Context(), null);

        //Then
        assertThat(results, is(nullValue()));
    }
}
