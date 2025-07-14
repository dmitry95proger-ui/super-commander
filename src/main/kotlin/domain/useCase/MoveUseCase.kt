package domain.useCase

import domain.model.ArrowDirection
import domain.model.ListMode
import domain.model.ResponseMove

class MoveUseCase {
    fun invoke(currentIndex: Int?, listCount: Int, arrowDirection: ArrowDirection, listMode: ListMode): ResponseMove {

        if(listCount == 0) return ResponseMove.Failure()
        if(currentIndex == null) return ResponseMove.Success(0)
        if(currentIndex < 0 ) return ResponseMove.Success(0)

        val newIndex = when(listMode) {
            ListMode.COLUMN -> {
                when(arrowDirection) {
                    ArrowDirection.UP -> if (currentIndex == 0) listCount - 1 else currentIndex - 1
                    ArrowDirection.DOWN -> if ((currentIndex + 1) == listCount) 0 else currentIndex + 1
                    ArrowDirection.LEFT -> { return ResponseMove.Failure() }
                    ArrowDirection.RIGHT -> { return ResponseMove.Failure() }
                }

            }
            ListMode.GRID -> {
                when(arrowDirection) {
                    ArrowDirection.UP -> if(currentIndex - 3 < 0) return ResponseMove.Failure() else currentIndex - 3
                    ArrowDirection.DOWN -> if(currentIndex + 4 > listCount) return ResponseMove.Failure() else currentIndex + 3
                    ArrowDirection.LEFT -> { if (currentIndex == 0) return ResponseMove.Failure() else currentIndex - 1 }
                    ArrowDirection.RIGHT -> { if ((currentIndex + 1) == listCount) return ResponseMove.Failure() else currentIndex + 1 }
                }
            }
            ListMode.BLOCK -> {
                when(arrowDirection) {
                    ArrowDirection.UP -> if(currentIndex - 2 < 0) return ResponseMove.Failure() else currentIndex - 2
                    ArrowDirection.DOWN -> if(currentIndex + 3 > listCount) return ResponseMove.Failure() else currentIndex + 2
                    ArrowDirection.LEFT -> { if (currentIndex == 0) return ResponseMove.Failure() else currentIndex - 1 }
                    ArrowDirection.RIGHT -> { if ((currentIndex + 1) == listCount) return ResponseMove.Failure() else currentIndex + 1 }
                }
            }
        }
        return ResponseMove.Success(newIndex)
    }
}