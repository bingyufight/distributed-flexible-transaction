package com.distributed.transaction.common;



/**
 * 
 * @author yubing
 *
 */
public interface Duplicatedable<T extends Duplicatedable<T>> extends Cloneable {
	T clone();
}
