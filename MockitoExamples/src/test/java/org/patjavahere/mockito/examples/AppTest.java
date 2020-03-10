package org.patjavahere.mockito.examples;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * How to initialize mockito annotations?
 * 1. Use @RunWith(MockitoJUnitRunner.class)
 * 2. Use MockitoAnnotations.initMocks(this)
 * 3. Use MockitoJUnit.rule()
 */
@RunWith(MockitoJUnitRunner.class)
public class AppTest {
    /**
     * If we dont use any of the 3 above mentioned, mock object will not be initialized and remains null
     */

    /*
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    */

    /*
      @Before
      public void setup() {
        MockitoAnnotations.initMocks(this);
      }
     */
    /**
     * @Mock annotation is used to create and inject mocked instances
     */
    @Mock
    private HashMap<String, Integer> mockHashMap;

    @Test
    public void saveTestUsingMockAnnotation() {
        mockHashMap.put("A", 1); //calling "put" method

        verify(mockHashMap, times(1)).put("A", 1);// checks whether the "put" method is called or not
        verify(mockHashMap, times(0)).get("A");// checks whether the "put" method is called or not

        assertEquals(0, mockHashMap.size());
    }

    /**
     * @Spy annotation is used to create a real object and spy on that real object
     */
    @Spy
    private HashMap<String, Integer> spyHashMap;

    @Test
    public void saveTestUsingSpyAnnotation() {
        spyHashMap.put("A", 10);

        verify(spyHashMap, times(1)).put("A", 10);
        verify(spyHashMap, times(0)).get("A");

        assertEquals(1, spyHashMap.size());
        assertEquals(Integer.valueOf(10), spyHashMap.get("A"));
    }

    /*************************************************************************
     *               @Mock          *              @Spy                      *
     *  - creates a shell instance  * - creates a real instance of the class *
     * of the Class                 * - track every interactions with it     *                                            *
     *  - is not a real object      * - maintains the state changes to it    *                                                         *
     *  - does not maintain the     *                                        *
     * state changes to it          *                                        *
     *************************************************************************/

    @Mock
    private HashMap<String, Integer> captorHashMap;

    /**
     * @Captor annotation is used to create an ArgumentCaptor instance,
     * which is used to capture method argument values for further assertions
     */
    @Captor
    private ArgumentCaptor<String> keyCaptor;

    @Captor
    private ArgumentCaptor<Integer> valueCaptor;

    @Test
    public void saveTestUsingCaptorAnnotation() {
        captorHashMap.put("A", 10);

        verify(captorHashMap).put(keyCaptor.capture(), valueCaptor.capture());

        assertEquals("A", keyCaptor.getValue());
        assertEquals(Integer.valueOf(10), valueCaptor.getValue());
    }

    /**
     * @InjectMocks
     *
     * - to create class instances which needs to be tested in test class.
     * - when actual method body needs to be executed for a given class.
     * - when we need all internal dependencies initialized with mock objects to work method correctly.
     *
     * @Mock
     *
     * - to create mocks which are needed to support testing of class to be tested.
     * - annotated class (to be tested) dependencies with @Mock annotation.
     *
     * Don't forget to define the when-thenReturn methods for mock objects
     * which class methods will be invoking during actual test execution
     */

    @InjectMocks
    private ServiceClass serviceClass;
    @Mock
    private DBDaoClass dbDaoClass;
    @Mock
    private NwDaoClass nwDaoClass;

    @Test
    public void validateTest() {
        boolean saved = serviceClass.save("temp.txt");
        assertTrue(saved);

        // to go more deep
        verify(dbDaoClass, times(1)).save("temp.txt");
        verify(nwDaoClass, times(1)).save("temp.txt");
    }
}
