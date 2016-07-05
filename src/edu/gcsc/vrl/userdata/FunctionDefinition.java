package edu.gcsc.vrl.userdata;

import java.io.Serializable;

/**
 *
 * @author mbreit
 */
public class FunctionDefinition implements Serializable 
{
    private static final long serialVersionUID = 1L;
    
    private FunctionDefinitionObservable.FctData fctData;
    
    public FunctionDefinition()
    {
        fctData = new FunctionDefinitionObservable.FctData();
    }
    
    public FunctionDefinition(FunctionDefinitionObservable.FctData _fctData)
    {
        fctData = _fctData;
    }
    
    public FunctionDefinitionObservable.FctData getFctData()
    {
        return fctData;
    }
    
    public void setFctData(FunctionDefinitionObservable.FctData _fctData)
    {
        fctData = _fctData;
    }
    
}