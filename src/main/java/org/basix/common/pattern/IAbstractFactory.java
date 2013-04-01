package org.basix.common.pattern;

/**
 * The abstract factory pattern is a software creation design pattern that provides a way to encapsulate
 * a group of individual factories that have a common theme without specifying their concrete classes.
 * @author  abilhakim
 * Date: 11/12/12
 * Time: 10:57 PM
 */
public interface IAbstractFactory {

    Object createObject();

}
