package base;

import gameinterface.InvalidRangeException;

public class MyInvalidRangeException extends Throwable implements InvalidRangeException{

    public MyInvalidRangeException(String message) {
        super(message);
    }
}
