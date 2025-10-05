from collections import deque

def solution(game_board, table):
    
    # 상 우 하 좌 순서
    dx = [-1, 0, 1, 0]
    dy = [0, 1, 0, -1]
    
    # (0,0) 으로 변환시키는 함수
    def normalize(shape):
        xs, ys = zip(*shape)
        min_x, min_y = min(xs), min(ys)
        norm = sorted([[x - min_x, y - min_y] for x, y in shape])
        return norm

    # 90도 회전 함수 (x, y) -> (y, -x)
    def rotate(shape):
        return [[y, -x] for x, y in shape]
    
    # 4번 회전시켜주는 함수
    def all_rotations(shape):
        shapes = []
        cur = shape
        for _ in range(4):
            cur = rotate(cur)
            cur = normalize(cur)
            shapes.append(cur)
        return shapes
    
    # 퍼즐 찾아주는 함수
    def findpuzzle(startX, startY):
        q = deque([[startX, startY]])
        puzzle = [[startX, startY]]
        table[startX][startY] = 0
        while q:
            x, y = q.popleft()
            for i in range(4):
                newX = x+dx[i]
                newY = y+dy[i]
                
                if not (0 <= newX < len(table) and 0 <= newY < len(table)):
                    continue
                
                if table[newX][newY] == 1:
                    puzzle.append([newX, newY])
                    table[newX][newY] = 0
                    q.append([newX, newY])
        return puzzle
    
    # 칸 찾기 함수 
    def findboard(startX, startY):
        q2 = deque([[startX, startY]])
        board = [[startX, startY]]
        game_board[startX][startY] = 1
        while q2:
            x, y = q2.popleft()
            for i in range(4):
                newX = x+dx[i]
                newY = y+dy[i]
                
                if not (0 <= newX < len(table) and 0 <= newY < len(table)):
                    continue
                
                if game_board[newX][newY] == 0:
                    board.append([newX, newY])
                    game_board[newX][newY] = 1
                    q2.append([newX, newY])
        return board if len(board) > 0 else []

    # 퍼즐들 찾기
    puzzle_all = []
    for i in range(len(table)):
        for j in range(len(table)):
            if table[i][j] == 0:
                continue
            else: 
                newPuzzle = findpuzzle(i, j)
                puzzle_all.append(newPuzzle)
    
    # 보드 칸 찾기
    board_all = []
    for i in range(len(table)):
        for j in range(len(table)):
            if game_board[i][j] == 1:
                continue
            else: 
                newBoard = findboard(i, j)
                normBoard = normalize(newBoard)
                board_all.append(normBoard)
    
    # 비교!
    count = 0
    used = [False] * len(puzzle_all)
    for board in board_all:
        #print("정렬된 board:", board)
        for i, puzzle in enumerate(puzzle_all):
            if used[i]:
                continue
            for rotatePuzzle in all_rotations(puzzle):
                #print("로테이션 퍼즐 상태:", rotatePuzzle)
                if rotatePuzzle==board:
                    #print("맞는 퍼즐을 찾았다!")
                    #print("칸 개수:", len(board))
                    used[i] = True
                    count += len(board)
                    break
            # 칸에 맞는거 찾으면 퍼즐도 더이상 볼필요없음
            if used[i]:
                break
            
    answer = count
    
    return answer