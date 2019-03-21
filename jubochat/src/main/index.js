'use strict'

import { app, BrowserWindow } from 'electron'
// const portfinder = require('detect-port')

/**
 * Set `__static` path to static files in production
 * https://simulatedgreg.gitbooks.io/electron-vue/content/en/using-static-assets.html
 */
if (process.env.NODE_ENV !== 'development') {
  global.__static = require('path')
    .join(__dirname, '/static')
    .replace(/\\/g, '\\\\')
}

// let io, socket
// let testApi
let mainWindow
const winURL =
  process.env.NODE_ENV === 'development'
    ? `http://localhost:9080`
    : `file://${__dirname}/index.html`

function createWindow () {
  /**
   * Initial window options
   */
  mainWindow = new BrowserWindow({
    height: 618,
    useContentSize: true,
    width: 1000
  })

  mainWindow.loadURL(winURL)

  mainWindow.on('closed', () => {
    mainWindow = null
  })
}

// const path = require('path')
// // const os = require('os')
// let apiProcess = null

// function writeLog (msg) {
//   console.log(msg)
// }

// function startAspCoreBackend () {
//   const spawn = require('child_process').spawn
//   let apipath = path.join(__dirname, '..\\api\\bin\\dist\\win\\api.exe')
//   apiProcess = spawn(apipath)
//   apiProcess.stdout.on('data', data => {
//     writeLog(`stdout: ${data}`)
//   })
// }

// function startSocketApiBridge (port) {
//   startAspCoreBackend(port)
// }

app.on('ready', () => {
  if (mainWindow == null) {
    createWindow()
  }
  // startAspCoreBackend()
  // portfinder(8080, (error, port) => {
  //   startSocketApiBridge(port)
  //   console.log(error)
  // })
})

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit()
  }
})

app.on('activate', () => {
  if (mainWindow === null) {
    createWindow()
  }
})

/**
 * Auto Updater
 *
 * Uncomment the following code below and install `electron-updater` to
 * support auto updating. Code Signing with a valid certificate is required.
 * https://simulatedgreg.gitbooks.io/electron-vue/content/en/using-electron-builder.html#auto-updating
 */

/*
import { autoUpdater } from 'electron-updater'

autoUpdater.on('update-downloaded', () => {
  autoUpdater.quitAndInstall()
})

app.on('ready', () => {
  if (process.env.NODE_ENV === 'production') autoUpdater.checkForUpdates()
})
 */
