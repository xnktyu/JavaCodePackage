#!/usr/bin/python3

import sys
import time
import pyautogui

from PIL import ImageGrab

imgDir = "D:/dang/python/dang/img"
imgLoadFailMark = "%s/%s.bmp" % (imgDir, "loadFailMark")
imgLoginPassMark = "%s/%s.bmp" % (imgDir, "loginPassMark")

screenWidth, screenHeight = pyautogui.size()

bookDir = "D:/book/数据结构(谭国律,肖随贵)"
bookDir2 = "D:/book/tmpbmp"
pageCount = 196
pageLeftArea = (186, 0, 186 + 770, screenHeight)
pageRightArea = (962, 0, 962 + 770, screenHeight)


def marchMark(fileMark):
	return pyautogui.locateCenterOnScreen(fileMark, grayscale=True)


def catchImage(area, file):
	im = ImageGrab.grab(bbox=area)
	im.save(file)


def checkMark():
	markResult = marchMark(imgLoadFailMark)
	if markResult:
		print("imgLoadFailMark", markResult)
		pyautogui.click(markResult)
		time.sleep(1)
		return checkMark()
	markResult = marchMark(imgLoginPassMark)
	if markResult:
		print("imgLoginPassMark", markResult)
		pyautogui.moveTo(markResult)
		return False
	return True


def isLeftPage(i):
	return i % 2 == 0


def catchPage(i):
	if isLeftPage(i):
		if checkMark():
			# print("catch", i)
			catchImage(pageLeftArea, "%s/%d.jpg" % (bookDir, i))
			catchImage(pageLeftArea, "%s/%d.bmp" % (bookDir2, i))
			return True
		else:
			return False
	else:
		if checkMark():
			# print("catch", i)
			catchImage(pageRightArea, "%s/%d.jpg" % (bookDir, i))
			catchImage(pageRightArea, "%s/%d.bmp" % (bookDir2, i))
			return True
		else:
			return False


def startImpl(i):
	while i <= pageCount:
		if catchPage(i):
			if isLeftPage(i):
				i += 1
			else:
				pyautogui.press('right')
				time.sleep(1)
				i += 1
		else:
			break
	else:
		return True
	return False


def start(i):
	if startImpl(i):
		print("catch success")
	else:
		print("catch fail")


def catchMark(area, fileMark):
	catchImage(area, fileMark)
	markResult = marchMark(fileMark)
	if markResult:
		print("markResult", markResult)
		pyautogui.moveTo(markResult)


print("ready...")
time.sleep(3)
print("go")

start(1)
# catchPage(xxx)

# catchMark((358, 448, 440, 448 + 1), imgLoadFailMark)
# catchMark((666, 510, 856, 510 + 1), imgLoginPassMark)

# im = pyautogui.screenshot(bookDir + "/1.jpg", region=(0, 0, 300, 400))
