package net.chessbuddies.engine.evaluators;

import net.chessbuddies.engine.Board;

/**
 * An interface used to create a static evaluator.
 *
 * @author Gary Blackwood
 */
public interface Evaluator {

  public int evaluate( Board board );

}