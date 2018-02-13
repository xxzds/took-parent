--分割函数
function string.split(str, delimiter)
	if str==nil or str=='' or delimiter==nil then
		return nil
	end
	
    local result = {}
    for match in (str..delimiter):gmatch("(.-)"..delimiter) do
        table.insert(result, match)
    end
    return result
end

--获取当前页
local currentPage = redis.call('HGET', KEYS[1], ARGV[1])
local result = {}
result['currentPage'] = currentPage

local tabl = string.split(ARGV[1], '_');
result['cateId'] = tabl[1]
result['keyword'] = tabl[2]
local maxPage = tabl[3]

 if tonumber(currentPage) < tonumber(maxPage) then
 	 redis.call('HINCRBY', KEYS[1], ARGV[1], 1)
 else
     redis.call('HSET', KEYS[1], ARGV[1] , 1)
 end
 local encodestr = cjson.encode(result)
return  encodestr