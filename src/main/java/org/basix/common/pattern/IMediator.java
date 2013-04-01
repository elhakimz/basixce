package org.basix.common.pattern;


import org.basix.common.pattern.mvc.IView;

/**
 * A View cant be accessed directly by other view
 * so a Mediator should maintain the access.
 * User: abilhakim
 * Date: 11/8/12
 * Time: 3:12 PM
 */
public interface IMediator {

    IView getView();

}
