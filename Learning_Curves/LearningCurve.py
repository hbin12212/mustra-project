import matplotlib.pyplot as plt
import numpy as np

## 예제 생성하기
np.random.seed(9)
xpoints = np.arange(0,100,10)
ypoints = [np.arange(10), np.arange(10)**2, np.random.choice(100, size = 10)]

## 선 옵션 설정
styles = ['dashed', 'dashed', 'dashed']
colors = ['deepskyblue', 'mediumseagreen','hotpink']
widths = [2, 2, 2]

## 출력할 이미지 크기
plt.figure(figsize=(8, 5))

## 점찍기 (그래프 그리기)
for i in range(len(ypoints)):
    plt.plot(xpoints, ypoints[i], linestyle = styles[i], color = colors[i], linewidth = widths[i])

## 그래프 이름 설정
plt.title("Learning Curves")
plt.xlabel("instance")
plt.ylabel("accuracy")

# x축 최소최대값
plt.xlim([0,100])

# y축 최소최대값
plt.ylim([0,100]) 

## 그래프 그림 저장
plt.savefig("graphexamples.png", dpi=350)

## 그래프 화면에 출력
plt.show()