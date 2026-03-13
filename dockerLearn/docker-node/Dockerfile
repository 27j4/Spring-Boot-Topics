FROM node:18

# Create app directory
WORKDIR /app

# Copy package files first to leverage Docker cache
COPY package.json package-lock.json ./

# Install app dependencies
RUN npm install

# Copy the rest of the application code
COPY main.js ./

# Expose the port defined in main.js
EXPOSE 8080

# Start the application
ENTRYPOINT [ "node", "main.js" ]

# this is to build the image 
# docker build -t docker-nodejs .