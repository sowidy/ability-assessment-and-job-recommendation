import sys


def main():
    # 获取命令行参数
    if len(sys.argv) > 1:
        argument = sys.argv[1]  # 获取第一个参数

        # 打印接收到的参数
        print("Received argument:", argument)

        # 在这里编写你的逻辑，例如对参数进行处理
        # 示例：将参数转换为整数并计算平方
        try:
            value = int(argument)
            result = value ** 2  # 计算平方
            print(f"The square of {value} is {result}.")
            # 如果需要，可以根据你的逻辑返回特定的退出码
            sys.exit(0)
        except ValueError:
            print("Invalid input: argument must be an integer.")
            sys.exit(1)
    else:
        print("No argument received.")
        sys.exit(1)


if __name__ == "__main__":
    main()
