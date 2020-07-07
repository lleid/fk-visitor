import cloneDeep from 'lodash.clonedeep'

export function convertToFullPath (routes) {
  if (!routes) return []

  routes = cloneDeep(routes)

  let queue = Array.isArray(routes) ? routes.concat() : [routes]
  while (queue.length) {
    const levelSize = queue.length
    for (let i = 0; i < levelSize; i++) {
      const node = queue.shift()
      if (!node.children || !node.children.length) continue
      node.children.forEach(child => {
        if (child.path[0] !== '/' && !child.path.startsWith('http')) {
          child.path = node.path.replace(/(\w*)[/]*$/, `$1/${child.path}`)
        }
      })
      queue = queue.concat(node.children)
    }
  }
  return routes
}

export function getPaths (routes) {
  const _routes = convertToFullPath(routes)
  const _paths = []
  let queue = Array.isArray(_routes) ? _routes.concat() : [_routes]
  while (queue.length) {
    const queueSize = queue.length
    for (let i = 0; i < queueSize; i++) {
      const node = queue.shift()
      _paths.push(node.path)
      if (!node.children || !node.children.length) continue
      queue = queue.concat(node.children)
    }
  }
  return _paths
}

export function getNames (routes) {
  const _names = []
  let queue = Array.isArray(routes) ? routes.concat() : [routes]
  while (queue.length) {
    const queueSize = queue.length
    for (let i = 0; i < queueSize; i++) {
      const node = queue.shift()
      _names.push(node.name)
      if (!node.children || !node.children.length) continue
      queue = queue.concat(node.children)
    }
  }
  return _names
}
