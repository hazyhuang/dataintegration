package com.hazy.imp;

import java.io.IOException;


public interface ISimpleTaskMan extends INode{
	 
    public void execute();

    public void loadActions() throws IOException;




}
