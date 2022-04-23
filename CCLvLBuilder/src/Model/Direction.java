package Model;

public enum Direction {
        North, South,
        East, West,
        Up, Down;

        public static Direction intToDir(int n) {
                switch(n) {
                        case 0:
                                return North;
                        case 1:
                                return South;
                        case 2:
                                return East;
                        case 3:
                                return West;
                        case 4:
                                return Up;
                        case 5:
                                return Down;
                        default:
                                return null;
                }
        }
}
