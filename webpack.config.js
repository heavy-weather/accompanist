const path = require('path');

module.exports = {
    entry: './src/main/html/index.js',
    output: {
        filename: 'index.bundle.js',
        path: path.resolve("./src/main/resources/static/"),
    },
    module: {
        rules: [
            {test: /\.svelte$/, use: "svelte-loader"}
        ]
    },
    mode: "development"
};