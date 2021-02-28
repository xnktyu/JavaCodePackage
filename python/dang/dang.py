#!/usr/bin/python3

import os
import time
import pyautogui

from PIL import ImageGrab

imgDir = "G:/wangzhiting/python/dang/img"
imgLoadingMark = "%s/%s.bmp" % (imgDir, "loadingMark")
imgLoadFailMark = "%s/%s.bmp" % (imgDir, "loadFailMark")
imgLoginPassMark = "%s/%s.bmp" % (imgDir, "loginPassMark")

bookDir = "G:/wangzhiting/python/output/社会科学与实际社会"
bookDir2 = "G:/wangzhiting/python/output/tmp"
pageCount = 93
pageArea = (550, 46, 1372, 1080)


def mkdir(path):
	if not os.path.exists(path):
		os.makedirs(path)


def marchMark(fileMark):
	return pyautogui.locateCenterOnScreen(fileMark, grayscale=True)


def catchImage(area, file):
	im = ImageGrab.grab(bbox=area)
	im.save(file)


def checkMark():
	if markResult := marchMark(imgLoadingMark):
		print("imgLoadingMark", markResult)
		time.sleep(3)
		return checkMark()
	if markResult := marchMark(imgLoadFailMark):
		print("imgLoadFailMark", markResult)
		pyautogui.click(markResult)
		time.sleep(3)
		return checkMark()
	if markResult := marchMark(imgLoginPassMark):
		print("imgLoginPassMark", markResult)
		pyautogui.moveTo(markResult)
		return False
	return True


def catchPage(i):
	if checkMark():
		# print("catch", i)
		mkdir(bookDir)
		catchImage(pageArea, "%s/%d.jpg" % (bookDir, i))
		mkdir(bookDir2)
		catchImage(pageArea, "%s/%d.bmp" % (bookDir2, i))
		return True
	else:
		return False


def startImpl(i):
	while i <= pageCount:
		if catchPage(i):
			pyautogui.press('right')
			# time.sleep(1)
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
# catchPage(1948)

# while True: pyautogui.hotkey('ctrl', 'end')

# catchMark((430, 558, 523, 558 + 1), imgLoadingMark)
# catchMark((358, 448, 440, 448 + 1), imgLoadFailMark)
# catchMark((666, 510, 856, 510 + 1), imgLoginPassMark)

# im = pyautogui.screenshot(bookDir + "/1.jpg", region=(0, 0, 300, 400))
