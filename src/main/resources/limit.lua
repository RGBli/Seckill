-- 计数限流 Lua 脚本

-- 资源唯一标志位
local key = KEYS[1]

-- 限流大小
local limit = tonumber(ARGV[1])

-- 获取当前流量大小
local currentLimit = 0

-- 过期则重置计数器的值为0，并设置过期时间
local existKey = redis.call('get', key)
if existKey == false then
    redis.call('set', key, 0)
    redis.call("EXPIRE", key, 1)
else
    currentLimit = existKey
end

if currentLimit + 1 > limit then
    -- 达到限流大小 返回
    return 0
else
    -- 没有达到阈值则 value + 1
    redis.call("INCRBY", key, 1)
    return currentLimit + 1
end